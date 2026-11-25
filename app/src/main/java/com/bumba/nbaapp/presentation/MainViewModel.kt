package com.bumba.nbaapp.presentation

import androidx.lifecycle.ViewModel
import com.bumba.nbaapp.domain.model.Player
import com.bumba.nbaapp.domain.model.Team
import com.bumba.nbaapp.domain.repository.PlayerRepository
import com.bumba.nbaapp.domain.repository.TeamRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MainUiState(
    val teams: List<Team> = emptyList(),
    val players: List<Player> = emptyList(),
    val selectedSort: String = "Name"
)

class MainViewModel(
    private val teamRepository: TeamRepository,
    private val playerRepository: PlayerRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    private val _teams = MutableStateFlow<List<Team>>(emptyList())
    val teams: StateFlow<List<Team>> = _teams.asStateFlow()

    private val _players = MutableStateFlow<List<Player>>(emptyList())
    val players: StateFlow<List<Player>> = _players.asStateFlow()

    private val _selectedSort = MutableStateFlow("Name")
    val selectedSort: StateFlow<String> = _selectedSort.asStateFlow()

    init {
        getTeams()
        getPlayers()
    }

    fun getTeams() {
        val loadedTeams = teamRepository.getTeams()
        _teams.value = loadedTeams
        _uiState.update { it.copy(teams = loadedTeams) }
    }

    fun getPlayers() {
        val loadedPlayers = playerRepository.getPlayers()
        _players.value = loadedPlayers
        _uiState.update { it.copy(players = loadedPlayers) }
    }

    fun onSortClick() {
        val sorted = _teams.value.sortedBy { it.name }
        _teams.value = sorted
        _uiState.update { it.copy(teams = sorted) }
    }
}
