package com.bumba.nbaapp.data.repository

import com.bumba.nbaapp.domain.model.Team
import com.bumba.nbaapp.domain.repository.TeamRepository

class TeamRepositoryImpl : TeamRepository {

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
}
