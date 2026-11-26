package com.bumba.nbaapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bumba.nbaapp.data.repository.NbaRepositoryImpl
import com.bumba.nbaapp.presentation.components.MainBottomBar
import com.bumba.nbaapp.presentation.components.TeamBottomSheet
import com.bumba.nbaapp.presentation.tabs.HomeScreen
import com.bumba.nbaapp.presentation.tabs.PlayerScreen
import com.bumba.nbaapp.presentation.theme.NBAAppTheme

enum class MainTab { HOME, PLAYERS }

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        viewModelFactory {
            initializer {
                MainViewModel(
                    nbaRepository = NbaRepositoryImpl()
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NBAAppTheme {
                var currentTab by rememberSaveable { mutableStateOf(MainTab.HOME) }
                val homeState by viewModel.homeState.collectAsStateWithLifecycle()
                val playerState by viewModel.playerState.collectAsStateWithLifecycle()
                val selectedTeam by viewModel.selectedTeam.collectAsStateWithLifecycle()
                val teamBottomSheetState by viewModel.teamBottomSheetState.collectAsStateWithLifecycle()
                val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

                Scaffold(
                    bottomBar = {
                        MainBottomBar(
                            currentTab = currentTab,
                            onTabSelected = { currentTab = it }
                        )
                    }
                ) { paddingValues ->
                    Box(Modifier.padding(paddingValues)) {
                        when (currentTab) {
                            MainTab.HOME -> {
                                HomeScreen(
                                    uiState = homeState,
                                    onSortClick = viewModel::onSortClick,
                                    onTeamClick = viewModel::onTeamClick,
                                    onFetchData = viewModel::getTeams
                                )
                            }

                            MainTab.PLAYERS -> {
                                PlayerScreen(
                                    uiState = playerState,
                                    onPlayerClick = { },
                                    onFetchData = viewModel::getPlayers
                                )
                            }
                        }
                    }
                }

                selectedTeam?.let {
                    TeamBottomSheet(
                        onDismiss = viewModel::onDismissBottomSheet,
                        sheetState = sheetState,
                        teamName = it.name,
                        uiState = teamBottomSheetState,
                        onFetchData = viewModel::getTeamGames
                    )
                }
            }
        }
    }
}
