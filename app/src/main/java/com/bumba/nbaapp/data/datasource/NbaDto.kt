package com.bumba.nbaapp.data.datasource

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MetaDto(
    @SerialName("next_cursor") val nextCursor: Int? = null,
    @SerialName("per_page") val perPage: Int? = null,
)

@Serializable
data class ApiResponse<T>(
    @SerialName("data") val data: List<T> = emptyList(),
    @SerialName("meta") val meta: MetaDto? = null,
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
    @SerialName("position") val position: String? = null,
    @SerialName("height") val height: String? = null,
    @SerialName("weight") val weight: String? = null,
    @SerialName("jersey_number") val jerseyNumber: String? = null,
    @SerialName("college") val college: String? = null,
    @SerialName("country") val country: String? = null,
    @SerialName("draft_year") val draftYear: Int? = null,
    @SerialName("draft_round") val draftRound: Int? = null,
    @SerialName("draft_number") val draftNumber: Int? = null,
    @SerialName("team") val team: TeamDto? = null,
)

@Serializable
data class GameDto(
    @SerialName("id") val id: Int? = null,
    @SerialName("date") val date: String? = null,
    @SerialName("season") val season: Int? = null,
    @SerialName("status") val status: String? = null,
    @SerialName("period") val period: Int? = null,
    @SerialName("time") val time: String? = null,
    @SerialName("postseason") val postseason: Boolean? = null,
    @SerialName("postponed") val postponed: Boolean? = null,
    @SerialName("home_team_score") val homeTeamScore: Int? = null,
    @SerialName("visitor_team_score") val visitorTeamScore: Int? = null,
    @SerialName("home_team") val homeTeam: TeamDto? = null,
    @SerialName("visitor_team") val visitorTeam: TeamDto? = null,
)
