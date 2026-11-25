package com.bumba.nbaapp.data.repository

import com.bumba.nbaapp.domain.model.Player
import com.bumba.nbaapp.domain.repository.PlayerRepository

class PlayerRepositoryImpl : PlayerRepository {

    override fun getPlayers(): List<Player> {
        return listOf(
            Player(
                firstName = "Žydrūnas",
                lastName = "Ilgauskas",
                teamName = "Cleavland Cavaliers"
            ),
            Player(
                firstName = "LeBron",
                lastName = "James",
                teamName = "Los Angeles Lakers"
            ),
            Player(
                firstName = "Stephen",
                lastName = "Curry",
                teamName = "Golden State Warriors"
            ),
            Player(
                firstName = "Kevin",
                lastName = "Durant",
                teamName = "Phoenix Suns"
            ),
            Player(
                firstName = "Giannis",
                lastName = "Antetokounmpo",
                teamName = "Milwaukee Bucks"
            ),
            Player(
                firstName = "Luka",
                lastName = "Dončić",
                teamName = "Dallas Mavericks"
            ),
            Player(
                firstName = "Nikola",
                lastName = "Jokić",
                teamName = "Denver Nuggets"
            ),
            Player(
                firstName = "Jayson",
                lastName = "Tatum",
                teamName = "Boston Celtics"
            ),
            Player(
                firstName = "Jimmy",
                lastName = "Butler",
                teamName = "Miami Heat"
            ),
            Player(
                firstName = "Trae",
                lastName = "Young",
                teamName = "Atlanta Hawks"
            ),
            Player(
                firstName = "Joel",
                lastName = "Embiid",
                teamName = "Philadelphia 76ers"
            ),
            Player(
                firstName = "Devin",
                lastName = "Booker",
                teamName = "Phoenix Suns"
            ),
            Player(
                firstName = "Anthony",
                lastName = "Davis",
                teamName = "Los Angeles Lakers"
            ),
            Player(
                firstName = "Damian",
                lastName = "Lillard",
                teamName = "Milwaukee Bucks"
            ),
            Player(
                firstName = "Shai",
                lastName = "Gilgeous-Alexander",
                teamName = "Oklahoma City Thunder"
            )
        )
    }
}
