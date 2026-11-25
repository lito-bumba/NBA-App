package com.bumba.nbaapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumba.nbaapp.core.presentation.UiState
import com.bumba.nbaapp.core.presentation.toErrorUiState
import com.bumba.nbaapp.domain.model.Team
import com.bumba.nbaapp.domain.repository.NbaRepository
import com.bumba.nbaapp.presentation.components.TeamBottomSheetState
import com.bumba.nbaapp.presentation.tabs.HomeTabState
import com.bumba.nbaapp.presentation.tabs.PlayerTabState
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val nbaRepository: NbaRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel() {

    val homeState: StateFlow<UiState<HomeTabState>>
        field = MutableStateFlow<UiState<HomeTabState>>(UiState.Loading)
    val playerState: StateFlow<UiState<PlayerTabState>>
        field = MutableStateFlow<UiState<PlayerTabState>>(UiState.Loading)
    val teamBottomSheetState: StateFlow<UiState<TeamBottomSheetState>>
        field = MutableStateFlow<UiState<TeamBottomSheetState>>(UiState.Loading)
    val selectedTeam: StateFlow<Team?>
        field = MutableStateFlow<Team?>(null)

    private var job: Job? = null

    fun getTeams() {
        homeState.value = UiState.Loading
        job?.cancel()
        job = viewModelScope.launch(dispatcher) {
            try {
                val loadedTeams = nbaRepository.getTeams()
                homeState.value = UiState.Success(
                    HomeTabState(
                        teams = loadedTeams,
                        selectedSort = "Name"
                    )
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                homeState.value = e.toErrorUiState()
            }
        }
    }

    fun getPlayers() {
        playerState.value = UiState.Loading
        job?.cancel()
        job = viewModelScope.launch(dispatcher) {
            try {
                val loadedPlayers = nbaRepository.getPlayers()
                playerState.value = UiState.Success(
                    PlayerTabState(
                        players = loadedPlayers,
                        selectedSort = ""
                    )
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                playerState.value = e.toErrorUiState()
            }
        }
    }

    fun onTeamClick(team: Team) {
        selectedTeam.value = team
        getTeamGames()
    }

    fun onDismissBottomSheet() {
        selectedTeam.value = null
    }

    fun getTeamGames() {
        teamBottomSheetState.value = UiState.Loading
        job?.cancel()
        job = viewModelScope.launch(dispatcher) {
            try {
                val teamId = selectedTeam.value?.id?.toIntOrNull()
                val loadedGames = nbaRepository.getGames(teamId)
                teamBottomSheetState.value = UiState.Success(
                    TeamBottomSheetState(games = loadedGames)
                )
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                teamBottomSheetState.value = e.toErrorUiState()
            }
        }
    }

    fun onSortClick(selectedSort: String = "Name") {
        homeState.update { currentState ->
            if (currentState is UiState.Success) {
                val sortedTeams = when (selectedSort.lowercase()) {
                    "city" -> currentState.data.teams.sortedBy { it.city.lowercase() }
                    "conference" -> currentState.data.teams.sortedBy { it.conference.lowercase() }
                    else -> currentState.data.teams.sortedBy { it.name.lowercase() }
                }
                UiState.Success(
                    currentState.data.copy(
                        teams = sortedTeams,
                        selectedSort = selectedSort
                    )
                )
            } else currentState
        }
    }
}
