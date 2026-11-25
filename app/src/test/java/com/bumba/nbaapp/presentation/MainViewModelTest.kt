package com.bumba.nbaapp.presentation

import com.bumba.nbaapp.core.presentation.UiState
import com.bumba.nbaapp.domain.model.Game
import com.bumba.nbaapp.domain.model.Player
import com.bumba.nbaapp.domain.model.Team
import com.bumba.nbaapp.fakes.FakeNbaRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private lateinit var fakeRepository: FakeNbaRepository
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: MainViewModel

    private val sampleTeams = listOf(
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
            city = "San Francisco",
            name = "Golden State Warriors",
            abbreviation = "GSW"
        )
    )

    private val samplePlayers = listOf(
        Player(firstName = "LeBron", lastName = "James", teamName = "Los Angeles Lakers"),
        Player(firstName = "Stephen", lastName = "Curry", teamName = "Golden State Warriors"),
        Player(firstName = "Giannis", lastName = "Antetokounmpo", teamName = "Milwaukee Bucks")
    )

    private val sampleGames = listOf(
        Game(
            homeTeamName = "Atlanta Hawks",
            homeTeamScore = "105",
            visitorTeamName = "Boston Celtics",
            visitorTeamScore = "98"
        ),
        Game(
            homeTeamName = "Atlanta Hawks",
            homeTeamScore = "112",
            visitorTeamName = "Cleveland Cavaliers",
            visitorTeamScore = "110"
        )
    )

    @Before
    fun setUp() {
        fakeRepository = FakeNbaRepository().apply {
            teams = sampleTeams
            players = samplePlayers
            gamesByTeamId = mapOf(1 to sampleGames)
            defaultGames = sampleGames
        }
        viewModel = MainViewModel(
            nbaRepository = fakeRepository,
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `initial states are loading or null`() {
        assertEquals(UiState.Loading, viewModel.homeState.value)
        assertEquals(UiState.Loading, viewModel.playerState.value)
        assertEquals(UiState.Loading, viewModel.teamBottomSheetState.value)
        assertNull(viewModel.selectedTeam.value)
    }

    @Test
    fun `getTeams success updates homeState with teams and default Name sort`() =
        runTest(testDispatcher) {
            viewModel.getTeams()

            assertEquals(UiState.Loading, viewModel.homeState.value)

            advanceUntilIdle()

            val state = viewModel.homeState.value
            assertTrue(state is UiState.Success)
            val successState = state as UiState.Success
            assertEquals(4, successState.data.teams.size)
            assertEquals("Name", successState.data.selectedSort)
            assertEquals(1, fakeRepository.getTeamsCallCount)
        }

    @Test
    fun `getTeams failure updates homeState with Error`() = runTest(testDispatcher) {
        fakeRepository.shouldThrowError = true
        fakeRepository.errorToThrow = IllegalStateException("Server error")

        viewModel.getTeams()
        advanceUntilIdle()

        val state = viewModel.homeState.value
        assertTrue(state is UiState.Error)
        val errorState = state as UiState.Error
        assertEquals("Server error", errorState.message)
        assertEquals(false, errorState.isInternetError)
    }

    @Test
    fun `getTeams internet error updates homeState with isInternetError true`() =
        runTest(testDispatcher) {
            fakeRepository.shouldThrowError = true
            fakeRepository.errorToThrow =
                java.net.UnknownHostException("Unable to resolve host: No address associated with hostname")

            viewModel.getTeams()
            advanceUntilIdle()

            val state = viewModel.homeState.value
            assertTrue(state is UiState.Error)
            val errorState = state as UiState.Error
            assertTrue(errorState.isInternetError)
        }

    @Test
    fun `onSortClick with Name orders teams alphabetically by name`() = runTest(testDispatcher) {
        viewModel.getTeams()
        advanceUntilIdle()

        viewModel.onSortClick("Name")

        val state = viewModel.homeState.value as UiState.Success
        assertEquals("Name", state.data.selectedSort)
        val teamNames = state.data.teams.map { it.name }
        assertEquals(
            listOf(
                "Atlanta Hawks",
                "Boston Celtics",
                "Cleveland Cavaliers",
                "Golden State Warriors"
            ), teamNames
        )
    }

    @Test
    fun `onSortClick with City orders teams alphabetically by city`() = runTest(testDispatcher) {
        viewModel.getTeams()
        advanceUntilIdle()

        viewModel.onSortClick("City")

        val state = viewModel.homeState.value as UiState.Success
        assertEquals("City", state.data.selectedSort)
        val cities = state.data.teams.map { it.city }
        assertEquals(listOf("Atlanta", "Boston", "Cleveland", "San Francisco"), cities)
    }

    @Test
    fun `onSortClick with Conference orders teams alphabetically by conference`() =
        runTest(testDispatcher) {
            viewModel.getTeams()
            advanceUntilIdle()

            viewModel.onSortClick("Conference")

            val state = viewModel.homeState.value as UiState.Success
            assertEquals("Conference", state.data.selectedSort)
            val conferences = state.data.teams.map { it.conference }
            assertEquals(listOf("East", "East", "East", "West"), conferences)
        }

    @Test
    fun `getPlayers success updates playerState with players`() = runTest(testDispatcher) {
        viewModel.getPlayers()

        assertEquals(UiState.Loading, viewModel.playerState.value)

        advanceUntilIdle()

        val state = viewModel.playerState.value
        assertTrue(state is UiState.Success)
        val successState = state as UiState.Success
        assertEquals(3, successState.data.players.size)
        assertEquals("LeBron", successState.data.players[0].firstName)
        assertEquals(1, fakeRepository.getPlayersCallCount)
    }

    @Test
    fun `getPlayers failure updates playerState with Error`() = runTest(testDispatcher) {
        fakeRepository.shouldThrowError = true
        fakeRepository.errorToThrow = IllegalStateException("Player load failed")

        viewModel.getPlayers()
        advanceUntilIdle()

        val state = viewModel.playerState.value
        assertTrue(state is UiState.Error)
        val errorState = state as UiState.Error
        assertEquals("Player load failed", errorState.message)
        assertEquals(false, errorState.isInternetError)
    }

    @Test
    fun `getPlayers internet error updates playerState with isInternetError true`() =
        runTest(testDispatcher) {
            fakeRepository.shouldThrowError = true
            fakeRepository.errorToThrow = java.net.ConnectException("Failed to connect")

            viewModel.getPlayers()
            advanceUntilIdle()

            val state = viewModel.playerState.value
            assertTrue(state is UiState.Error)
            val errorState = state as UiState.Error
            assertTrue(errorState.isInternetError)
        }

    @Test
    fun `onTeamClick sets selectedTeam and fetches team games`() = runTest(testDispatcher) {
        val selectedTeam = sampleTeams.first()

        viewModel.onTeamClick(selectedTeam)

        assertEquals(selectedTeam, viewModel.selectedTeam.value)
        assertEquals(UiState.Loading, viewModel.teamBottomSheetState.value)

        advanceUntilIdle()

        val state = viewModel.teamBottomSheetState.value
        assertTrue(state is UiState.Success)
        val successState = state as UiState.Success
        assertEquals(2, successState.data.games.size)
        assertEquals(1, fakeRepository.getGamesCallCount)
        assertEquals(1, fakeRepository.lastRequestedTeamId)
    }

    @Test
    fun `getTeamGames failure sets teamBottomSheetState to Error`() = runTest(testDispatcher) {
        fakeRepository.shouldThrowError = true
        fakeRepository.errorToThrow = RuntimeException("Timeout fetching games")

        viewModel.getTeamGames()
        advanceUntilIdle()

        val state = viewModel.teamBottomSheetState.value
        assertTrue(state is UiState.Error)
        val errorState = state as UiState.Error
        assertEquals("Timeout fetching games", errorState.message)
        assertEquals(false, errorState.isInternetError)
    }

    @Test
    fun `getTeamGames internet error sets teamBottomSheetState to Error with isInternetError true`() =
        runTest(testDispatcher) {
            fakeRepository.shouldThrowError = true
            fakeRepository.errorToThrow = java.net.SocketTimeoutException("Connect timed out")

            viewModel.getTeamGames()
            advanceUntilIdle()

            val state = viewModel.teamBottomSheetState.value
            assertTrue(state is UiState.Error)
            val errorState = state as UiState.Error
            assertTrue(errorState.isInternetError)
        }

    @Test
    fun `onDismissBottomSheet clears selectedTeam`() {
        val selectedTeam = sampleTeams.first()
        viewModel.onTeamClick(selectedTeam)
        assertEquals(selectedTeam, viewModel.selectedTeam.value)

        viewModel.onDismissBottomSheet()

        assertNull(viewModel.selectedTeam.value)
    }
}
