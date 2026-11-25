package com.bumba.nbaapp.data.datasource

import com.bumba.nbaapp.domain.model.Game
import com.bumba.nbaapp.domain.model.Player
import com.bumba.nbaapp.domain.model.Team

fun TeamDto.toDomain(): Team = Team(
    id = id?.toString().orEmpty(),
    conference = conference?.trim().orEmpty(),
    division = division?.trim().orEmpty(),
    city = city?.trim().orEmpty(),
    name = (fullName?.trim()?.takeIf { it.isNotBlank() } ?: name?.trim()).orEmpty(),
    abbreviation = abbreviation?.trim().orEmpty(),
)

fun PlayerDto.toDomain(): Player = Player(
    firstName = firstName?.trim().orEmpty(),
    lastName = lastName?.trim().orEmpty(),
    teamName = (team?.fullName?.trim()?.takeIf { it.isNotBlank() } ?: team?.name?.trim()).orEmpty(),
)

fun GameDto.toDomain(): Game = Game(
    homeTeamName = (homeTeam?.fullName?.trim()?.takeIf { it.isNotBlank() } ?: homeTeam?.name?.trim()).orEmpty(),
    homeTeamScore = (homeTeamScore ?: 0).toString(),
    visitorTeamName = (visitorTeam?.fullName?.trim()?.takeIf { it.isNotBlank() } ?: visitorTeam?.name?.trim()).orEmpty(),
    visitorTeamScore = (visitorTeamScore ?: 0).toString(),
)
