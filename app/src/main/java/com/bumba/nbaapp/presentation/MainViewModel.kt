package com.bumba.nbaapp.presentation

import androidx.lifecycle.ViewModel
import com.bumba.nbaapp.core.presentation.UiState
import com.bumba.nbaapp.domain.repository.NbaRepository
import com.bumba.nbaapp.presentation.tabs.HomeTabState
import com.bumba.nbaapp.presentation.tabs.PlayerTabState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val nbaRepository: NbaRepository
) : ViewModel() {

    val homeState: StateFlow<UiState<HomeTabState>>
        field = MutableStateFlow<UiState<HomeTabState>>(UiState.Loading)
    val playerState: StateFlow<UiState<PlayerTabState>>
        field = MutableStateFlow<UiState<PlayerTabState>>(UiState.Loading)

    fun getTeams() {
        try {
            val loadedTeams = nbaRepository.getTeams()
            homeState.value = UiState.Success(
                HomeTabState(
                    teams = loadedTeams,
                    selectedSort = "Name"
                )
            )
        } catch (e: Exception) {
            homeState.value = UiState.Error(
                isInternetError = false,
                message = e.localizedMessage
            )
        }
    }

    fun getPlayers() {
        try {
            val loadedPlayers = nbaRepository.getPlayers()
            playerState.value = UiState.Success(
                PlayerTabState(
                    players = loadedPlayers,
                    selectedSort = ""
                )
            )
        } catch (e: Exception) {
            playerState.value = UiState.Error(
                isInternetError = false,
                message = e.localizedMessage
            )
        }
    }

    fun onSortClick(currentSort: String = "Name") {
        homeState.update { currentState ->
            if (currentState is UiState.Success) {
                val sortedTeams = currentState.data.teams.sortedBy { it.name }
                UiState.Success(currentState.data.copy(teams = sortedTeams))
            } else currentState
        }
    }
}
