package com.bumba.nbaapp.data.repository

import com.bumba.nbaapp.data.datasource.NbaRemoteDataSource
import com.bumba.nbaapp.data.datasource.toDomain
import com.bumba.nbaapp.domain.model.Game
import com.bumba.nbaapp.domain.model.Player
import com.bumba.nbaapp.domain.model.Team
import com.bumba.nbaapp.domain.repository.NbaRepository

class NbaRepositoryImpl(
    private val remoteDataSource: NbaRemoteDataSource = NbaRemoteDataSource(),
) : NbaRepository {

    override suspend fun getTeams(): List<Team> {
        return remoteDataSource.getTeams()
            .map { it.toDomain() }
    }

    override suspend fun getPlayers(): List<Player> {
        return remoteDataSource.getPlayers()
            .map { it.toDomain() }
    }

    override suspend fun getGames(teamId: Int?): List<Game> {
        return remoteDataSource.getGames(teamId = teamId)
            .map { it.toDomain() }
    }
}
