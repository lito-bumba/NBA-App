package com.bumba.nbaapp.data.repository

import com.bumba.nbaapp.data.datasource.NbaRemoteDataSource
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert
import org.junit.Test

class NbaRepositoryIntegrationTest {

    private val teamsJson = """
        {
            "data": [
                {
                    "id": 1,
                    "conference": "East",
                    "division": "Southeast",
                    "city": "Atlanta",
                    "name": "Hawks",
                    "full_name": "Atlanta Hawks",
                    "abbreviation": "ATL"
                },
                {
                    "id": 2,
                    "conference": "East",
                    "division": "Atlantic",
                    "city": "Boston",
                    "name": "Celtics",
                    "full_name": "Boston Celtics",
                    "abbreviation": "BOS"
                }
            ],
            "meta": { "next_cursor": 25, "per_page": 25 }
        }
    """.trimIndent()

    private val playersJson = """
        {
            "data": [
                {
                    "id": 1,
                    "first_name": "Alex",
                    "last_name": "Abrines",
                    "position": "G",
                    "team": {
                        "id": 21,
                        "conference": "West",
                        "division": "Northwest",
                        "city": "Oklahoma City",
                        "name": "Thunder",
                        "full_name": "Oklahoma City Thunder",
                        "abbreviation": "OKC"
                    }
                }
            ],
            "meta": { "next_cursor": 25, "per_page": 25 }
        }
    """.trimIndent()

    private val gamesJson = """
        {
            "data": [
                {
                    "id": 875306,
                    "home_team_score": 105,
                    "visitor_team_score": 98,
                    "home_team": {
                        "id": 1,
                        "conference": "East",
                        "division": "Southeast",
                        "city": "Atlanta",
                        "name": "Hawks",
                        "full_name": "Atlanta Hawks",
                        "abbreviation": "ATL"
                    },
                    "visitor_team": {
                        "id": 50,
                        "conference": "    ",
                        "division": "",
                        "city": "",
                        "name": "Denver Nuggets",
                        "full_name": "Denver Nuggets",
                        "abbreviation": "DN"
                    }
                }
            ],
            "meta": { "next_cursor": 875086, "per_page": 25 }
        }
    """.trimIndent()

    private fun createRepositoryWithMockEngine(
        responseJson: String,
        status: HttpStatusCode = HttpStatusCode.OK,
        onVerifyRequest: (url: String, headers: Map<String, List<String>>) -> Unit = { _, _ -> }
    ): NbaRepositoryImpl {
        val mockEngine = MockEngine.Companion { request ->
            onVerifyRequest(
                request.url.toString(),
                request.headers.entries().associate { it.key to it.value })
            respond(
                content = responseJson,
                status = status,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val client = HttpClient(mockEngine) {
            expectSuccess = true
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        coerceInputValues = true
                    }
                )
            }
        }

        val remoteDataSource = NbaRemoteDataSource(client = client)
        return NbaRepositoryImpl(remoteDataSource = remoteDataSource)
    }

    @Test
    fun `getTeams integration parses remote json, handles authorization header, and maps to domain`() =
        runTest {
            var capturedUrl = ""
            var capturedAuthHeader = ""

            val repository = createRepositoryWithMockEngine(teamsJson) { url, headers ->
                capturedUrl = url
                capturedAuthHeader = headers["Authorization"]?.firstOrNull().orEmpty()
            }

            val teams = repository.getTeams()

            Assert.assertTrue(capturedUrl.contains("/v1/teams"))
            Assert.assertNotNull(capturedAuthHeader)
            Assert.assertEquals(2, teams.size)

            val hawks = teams[0]
            Assert.assertEquals("1", hawks.id)
            Assert.assertEquals("Atlanta Hawks", hawks.name)
            Assert.assertEquals("Atlanta", hawks.city)
            Assert.assertEquals("East", hawks.conference)
            Assert.assertEquals("ATL", hawks.abbreviation)
        }

    @Test
    fun `getPlayers integration parses remote json and maps player and inner team`() = runTest {
        var capturedUrl = ""
        val repository = createRepositoryWithMockEngine(playersJson) { url, _ ->
            capturedUrl = url
        }

        val players = repository.getPlayers()

        Assert.assertTrue(capturedUrl.contains("/v1/players"))
        Assert.assertEquals(1, players.size)

        val player = players[0]
        Assert.assertEquals("Alex", player.firstName)
        Assert.assertEquals("Abrines", player.lastName)
        Assert.assertEquals("Oklahoma City Thunder", player.teamName)
    }

    @Test
    fun `getGames integration passes team_ids query parameter, trims whitespace, and maps domain games`() =
        runTest {
            var capturedUrl = ""
            val repository = createRepositoryWithMockEngine(gamesJson) { url, _ ->
                capturedUrl = url
            }

            val games = repository.getGames(teamId = 1)

            Assert.assertTrue(capturedUrl.contains("team_ids%5B%5D=1") || capturedUrl.contains("team_ids[]=1"))
            Assert.assertEquals(1, games.size)

            val game = games[0]
            Assert.assertEquals("Atlanta Hawks", game.homeTeamName)
            Assert.assertEquals("105", game.homeTeamScore)
            Assert.assertEquals("Denver Nuggets", game.visitorTeamName)
            Assert.assertEquals("98", game.visitorTeamScore)
        }

    @Test(expected = Exception::class)
    fun `repository throws exception when remote returns HTTP 500 error`() = runTest {
        val repository = createRepositoryWithMockEngine(
            responseJson = """{"error": "Internal server error"}""",
            status = HttpStatusCode.InternalServerError
        )

        repository.getTeams()
    }
}