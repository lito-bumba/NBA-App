package com.bumba.nbaapp.data.repository

import com.bumba.nbaapp.domain.model.Game
import com.bumba.nbaapp.domain.model.Player
import com.bumba.nbaapp.domain.model.Team
import com.bumba.nbaapp.domain.repository.NbaRepository

class NbaRepositoryImpl : NbaRepository {
    override fun getTeams(): List<Team> {
        return listOf(
            Team(
                id = "1",
                conference = "East",
                division = "Southeast",
                city = "Atlanta",
                name = "Atlanta Hawks",
                abbreviation = "ATL"
            ),
            Team(
                id = "6",
                conference = "East",
                division = "Central",
                city = "Cleveland",
                name = "Cleveland Cavaliers",
                abbreviation = "CLE"
            ),
            Team(
                id = "2",
                conference = "East",
                division = "Atlantic",
                city = "Boston",
                name = "Boston Celtics",
                abbreviation = "BOS"
            ),
            Team(
                id = "9",
                conference = "West",
                division = "Pacific",
                city = "Golden State",
                name = "Golden State Warriors",
                abbreviation = "GSW"
            ),
            Team(
                id = "13",
                conference = "West",
                division = "Pacific",
                city = "Los Angeles",
                name = "Los Angeles Lakers",
                abbreviation = "LAL"
            ),
            Team(
                id = "5",
                conference = "East",
                division = "Central",
                city = "Chicago",
                name = "Chicago Bulls",
                abbreviation = "CHI"
            ),
            Team(
                id = "8",
                conference = "West",
                division = "Northwest",
                city = "Denver",
                name = "Denver Nuggets",
                abbreviation = "DEN"
            ),
            Team(
                id = "15",
                conference = "East",
                division = "Southeast",
                city = "Miami",
                name = "Miami Heat",
                abbreviation = "MIA"
            ),
            Team(
                id = "16",
                conference = "East",
                division = "Central",
                city = "Milwaukee",
                name = "Milwaukee Bucks",
                abbreviation = "MIL"
            ),
            Team(
                id = "23",
                conference = "West",
                division = "Pacific",
                city = "Phoenix",
                name = "Phoenix Suns",
                abbreviation = "PHX"
            ),
            Team(
                id = "7",
                conference = "West",
                division = "Southwest",
                city = "Dallas",
                name = "Dallas Mavericks",
                abbreviation = "DAL"
            ),
            Team(
                id = "22",
                conference = "East",
                division = "Atlantic",
                city = "Philadelphia",
                name = "Philadelphia 76ers",
                abbreviation = "PHI"
            ),
            Team(
                id = "19",
                conference = "East",
                division = "Atlantic",
                city = "New York",
                name = "New York Knicks",
                abbreviation = "NYK"
            ),
            Team(
                id = "20",
                conference = "West",
                division = "Northwest",
                city = "Oklahoma City",
                name = "Oklahoma City Thunder",
                abbreviation = "OKC"
            )
        )
    }

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
