package com.bumba.nbaapp.data.datasource

import org.junit.Assert.assertEquals
import org.junit.Test

class NbaMapperTest {

    @Test
    fun `TeamDto toDomain trims whitespace from conference, division, city and abbreviation`() {
        val dto = TeamDto(
            id = 50,
            conference = "    ",
            division = "   ",
            city = "  Denver  ",
            name = "Nuggets",
            fullName = "Denver Nuggets",
            abbreviation = " DN "
        )

        val domain = dto.toDomain()

        assertEquals("50", domain.id)
        assertEquals("", domain.conference)
        assertEquals("", domain.division)
        assertEquals("Denver", domain.city)
        assertEquals("Denver Nuggets", domain.name)
        assertEquals("DN", domain.abbreviation)
    }

    @Test
    fun `TeamDto toDomain falls back to name when fullName is blank or null`() {
        val dtoWithBlankFullName = TeamDto(
            id = 1,
            name = "Hawks",
            fullName = "   "
        )
        val dtoWithNullFullName = TeamDto(
            id = 2,
            name = "Celtics",
            fullName = null
        )

        val domain1 = dtoWithBlankFullName.toDomain()
        val domain2 = dtoWithNullFullName.toDomain()

        assertEquals("Hawks", domain1.name)
        assertEquals("Celtics", domain2.name)
    }

    @Test
    fun `TeamDto toDomain handles all null fields gracefully`() {
        val dto = TeamDto()

        val domain = dto.toDomain()

        assertEquals("", domain.id)
        assertEquals("", domain.conference)
        assertEquals("", domain.division)
        assertEquals("", domain.city)
        assertEquals("", domain.name)
        assertEquals("", domain.abbreviation)
    }

    @Test
    fun `PlayerDto toDomain trims names and extracts team fullName or name`() {
        val dtoWithFullNameTeam = PlayerDto(
            id = 1,
            firstName = "  LeBron ",
            lastName = " James  ",
            team = TeamDto(name = "Lakers", fullName = "Los Angeles Lakers")
        )
        val dtoWithNameOnlyTeam = PlayerDto(
            id = 2,
            firstName = "Stephen",
            lastName = "Curry",
            team = TeamDto(name = "Warriors", fullName = "")
        )
        val dtoWithNullTeam = PlayerDto(
            id = 3,
            firstName = "Free",
            lastName = "Agent",
            team = null
        )

        val player1 = dtoWithFullNameTeam.toDomain()
        val player2 = dtoWithNameOnlyTeam.toDomain()
        val player3 = dtoWithNullTeam.toDomain()

        assertEquals("LeBron", player1.firstName)
        assertEquals("James", player1.lastName)
        assertEquals("Los Angeles Lakers", player1.teamName)

        assertEquals("Stephen", player2.firstName)
        assertEquals("Curry", player2.lastName)
        assertEquals("Warriors", player2.teamName)

        assertEquals("", player3.teamName)
    }

    @Test
    fun `GameDto toDomain formats scores as strings and extracts team names`() {
        val dto = GameDto(
            id = 100,
            homeTeamScore = 102,
            visitorTeamScore = 99,
            homeTeam = TeamDto(fullName = "Atlanta Hawks"),
            visitorTeam = TeamDto(fullName = "Boston Celtics")
        )

        val game = dto.toDomain()

        assertEquals("Atlanta Hawks", game.homeTeamName)
        assertEquals("102", game.homeTeamScore)
        assertEquals("Boston Celtics", game.visitorTeamName)
        assertEquals("99", game.visitorTeamScore)
    }

    @Test
    fun `GameDto toDomain handles null scores by defaulting to 0`() {
        val dto = GameDto(
            id = 101,
            homeTeamScore = null,
            visitorTeamScore = null,
            homeTeam = null,
            visitorTeam = null
        )

        val game = dto.toDomain()

        assertEquals("0", game.homeTeamScore)
        assertEquals("0", game.visitorTeamScore)
        assertEquals("", game.homeTeamName)
        assertEquals("", game.visitorTeamName)
    }
}
