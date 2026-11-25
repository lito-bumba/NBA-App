package com.bumba.nbaapp.data.datasource

import com.bumba.nbaapp.BuildConfig
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
import kotlinx.serialization.json.Json

class NbaRemoteDataSource(
    private val client: HttpClient = createDefaultHttpClient()
) {

    suspend fun getTeams(): List<TeamDto> {
        val response: ApiResponse<TeamDto> = client.get("$BASE_URL/teams") {
            header(AUTHORIZATION_KEY, API_KEY)
        }.body()
        return response.data
    }

    suspend fun getPlayers(
        search: String? = null,
        cursor: Int? = null,
        perPage: Int? = null,
    ): List<PlayerDto> {
        val response: ApiResponse<PlayerDto> = client.get("$BASE_URL/players") {
            header(AUTHORIZATION_KEY, API_KEY)
            search?.takeIf { it.isNotBlank() }?.let { parameter("search", it) }
            cursor?.let { parameter("cursor", it) }
            perPage?.let { parameter("per_page", it) }
        }.body()
        return response.data
    }

    suspend fun getGames(
        teamId: Int? = null,
        cursor: Int? = null,
    ): List<GameDto> {
        val response: ApiResponse<GameDto> = client.get("$BASE_URL/games") {
            header(AUTHORIZATION_KEY, API_KEY)
            teamId?.let { parameter("team_ids[]", it) }
            cursor?.let { parameter("cursor", it) }
        }.body()
        return response.data
    }

    companion object {
        private const val BASE_URL = "https://api.balldontlie.io/v1"
        private const val AUTHORIZATION_KEY = "Authorization"
        private const val API_KEY = BuildConfig.API_KEY

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
