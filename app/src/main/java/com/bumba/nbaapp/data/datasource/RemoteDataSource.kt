package com.bumba.nbaapp.data.datasource

import com.bumba.nbaapp.domain.model.Game
import com.bumba.nbaapp.domain.model.Player
import com.bumba.nbaapp.domain.model.Team
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class ApiResponse<T>(
    @SerialName("data") val data: List<T> = emptyList(),
)

@Serializable
data class TeamDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("conference") val conference: String? = null,
    @SerialName("division") val division: String? = null,
    @SerialName("city") val city: String? = null,
    @SerialName("name") val name: String? = null,
    @SerialName("full_name") val fullName: String? = null,
    @SerialName("abbreviation") val abbreviation: String? = null,
)

@Serializable
data class PlayerDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("first_name") val firstName: String? = null,
    @SerialName("last_name") val lastName: String? = null,
    @SerialName("team") val team: TeamDto? = null,
)

@Serializable
data class GameDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("home_team") val homeTeam: TeamDto? = null,
    @SerialName("home_team_score") val homeTeamScore: Int? = null,
    @SerialName("visitor_team") val visitorTeam: TeamDto? = null,
    @SerialName("visitor_team_score") val visitorTeamScore: Int? = null,
)

fun TeamDto.toDomain(): Team = Team(
    id = id?.toString().orEmpty(),
    conference = conference.orEmpty(),
    division = division.orEmpty(),
    city = city.orEmpty(),
    name = fullName ?: name.orEmpty(),
    abbreviation = abbreviation.orEmpty(),
)

fun PlayerDto.toDomain(): Player = Player(
    firstName = firstName.orEmpty(),
    lastName = lastName.orEmpty(),
    teamName = team?.fullName ?: team?.name.orEmpty(),
)

fun GameDto.toDomain(): Game = Game(
    homeTeamName = homeTeam?.fullName ?: homeTeam?.name.orEmpty(),
    homeTeamScore = homeTeamScore?.toString() ?: "0",
    visitorTeamName = visitorTeam?.fullName ?: visitorTeam?.name.orEmpty(),
    visitorTeamScore = visitorTeamScore?.toString() ?: "0",
)

class RemoteDataSource(
    private val client: HttpClient = createDefaultHttpClient(),
    private val baseUrl: String = BASE_URL,
    private val apiKey: String? = null,
) {

    suspend fun getTeams(): List<Team> {
        val response: ApiResponse<TeamDto> = client.get("$baseUrl/teams") {
            apiKey?.let { header("Authorization", it) }
        }.body()
        return response.data.map { it.toDomain() }
    }

    suspend fun getPlayers(search: String? = null, page: Int? = null): List<Player> {
        val response: ApiResponse<PlayerDto> = client.get("$baseUrl/players") {
            apiKey?.let { header("Authorization", it) }
            search?.let { parameter("search", it) }
            page?.let { parameter("page", it) }
        }.body()
        return response.data.map { it.toDomain() }
    }

    suspend fun getGames(teamIds: List<Int>? = null, page: Int? = null): List<Game> {
        val response: ApiResponse<GameDto> = client.get("$baseUrl/games") {
            apiKey?.let { header("Authorization", it) }
            teamIds?.forEach { parameter("team_ids[]", it) }
            page?.let { parameter("page", it) }
        }.body()
        return response.data.map { it.toDomain() }
    }

    companion object {
        const val BASE_URL = "https://api.balldontlie.io/v1"

        fun createDefaultHttpClient(): HttpClient {
            return HttpClient(OkHttp) {
                install(ContentNegotiation) {
                    json(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                            coerceInputValues = true
                            encodeDefaults = true
                        },
                    )
                }
                install(Logging) {
                    level = LogLevel.INFO
                }
            }
        }
    }
}
