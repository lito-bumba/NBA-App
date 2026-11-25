package com.bumba.nbaapp.fakes

import com.bumba.nbaapp.domain.model.Game
import com.bumba.nbaapp.domain.model.Player
import com.bumba.nbaapp.domain.model.Team
import com.bumba.nbaapp.domain.repository.NbaRepository

class FakeNbaRepository : NbaRepository {

    var teams: List<Team> = emptyList()
    var players: List<Player> = emptyList()
    var gamesByTeamId: Map<Int?, List<Game>> = emptyMap()
    var defaultGames: List<Game> = emptyList()

    var shouldThrowError: Boolean = false
    var errorToThrow: Throwable = RuntimeException("Fake repository network error")

    var getTeamsCallCount: Int = 0
        private set
    var getPlayersCallCount: Int = 0
        private set
    var getGamesCallCount: Int = 0
        private set
    var lastRequestedTeamId: Int? = null
        private set

    override suspend fun getTeams(): List<Team> {
        getTeamsCallCount++
        if (shouldThrowError) throw errorToThrow
        return teams
    }

    override suspend fun getPlayers(): List<Player> {
        getPlayersCallCount++
        if (shouldThrowError) throw errorToThrow
        return players
    }

    override suspend fun getGames(teamId: Int?): List<Game> {
        getGamesCallCount++
        lastRequestedTeamId = teamId
        if (shouldThrowError) throw errorToThrow
        return gamesByTeamId[teamId] ?: defaultGames
    }

    fun reset() {
        getTeamsCallCount = 0
        getPlayersCallCount = 0
        getGamesCallCount = 0
        lastRequestedTeamId = null
        shouldThrowError = false
    }
}
