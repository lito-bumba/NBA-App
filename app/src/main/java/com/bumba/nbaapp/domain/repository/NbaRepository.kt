package com.bumba.nbaapp.domain.repository

import com.bumba.nbaapp.domain.model.Game
import com.bumba.nbaapp.domain.model.Player
import com.bumba.nbaapp.domain.model.Team

interface NbaRepository {

    fun getTeams(): List<Team>

    fun getPlayers(): List<Player>

    fun getGames(): List<Game>
}