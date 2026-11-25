package com.bumba.nbaapp.domain.repository

import com.bumba.nbaapp.domain.model.Game

interface GameRepository {

    fun getGames(): List<Game>
}