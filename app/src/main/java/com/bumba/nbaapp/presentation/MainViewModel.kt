package com.bumba.nbaapp.presentation

import androidx.lifecycle.ViewModel
import com.bumba.nbaapp.domain.model.Game
import com.bumba.nbaapp.domain.model.Player
import com.bumba.nbaapp.domain.model.Team
import com.bumba.nbaapp.domain.repository.NbaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val nbaRepository: NbaRepository
) : ViewModel() {

    init {
        getTeams()
        getPlayers()
    }

    fun getTeams() {
        val loadedTeams = nbaRepository.getTeams()
    }

    fun getPlayers() {
        val loadedPlayers = nbaRepository.getPlayers()
    }

    fun onSortClick() {
    }
}
