package com.bumba.nbaapp.data.repository

import com.bumba.nbaapp.domain.model.Game
import com.bumba.nbaapp.domain.repository.GameRepository

class GameRepositoryImpl : GameRepository {

    override fun getGames(): List<Game> {
        return listOf(
            Game(
                homeTeamName = "Atlanta Hawks",
                homeTeamScore = "103",
                visitorTeamName = "Chicago Bulls",
                visitorTeamScore = "103"
            ),
            Game(
                homeTeamName = "Boston Celtics",
                homeTeamScore = "112",
                visitorTeamName = "Miami Heat",
                visitorTeamScore = "106"
            ),
            Game(
                homeTeamName = "Los Angeles Lakers",
                homeTeamScore = "124",
                visitorTeamName = "Golden State Warriors",
                visitorTeamScore = "118"
            ),
            Game(
                homeTeamName = "Denver Nuggets",
                homeTeamScore = "115",
                visitorTeamName = "Phoenix Suns",
                visitorTeamScore = "109"
            ),
            Game(
                homeTeamName = "Milwaukee Bucks",
                homeTeamScore = "120",
                visitorTeamName = "Philadelphia 76ers",
                visitorTeamScore = "117"
            )
        )
    }
}
