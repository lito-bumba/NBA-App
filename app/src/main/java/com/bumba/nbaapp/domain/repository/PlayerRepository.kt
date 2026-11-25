package com.bumba.nbaapp.domain.repository

import com.bumba.nbaapp.domain.model.Player

interface PlayerRepository {

    fun getPlayers(): List<Player>
}