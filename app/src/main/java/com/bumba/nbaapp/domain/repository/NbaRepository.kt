package com.bumba.nbaapp.domain.repository

import com.bumba.nbaapp.domain.model.Game
import com.bumba.nbaapp.domain.model.Player
import com.bumba.nbaapp.domain.model.Team

interface NbaRepository {

    suspend fun getTeams(): List<Team>

    suspend fun getPlayers(): List<Player>

    suspend fun getGames(teamId: Int? = null): List<Game>
}
