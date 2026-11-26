package com.bumba.nbaapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumba.nbaapp.R
import com.bumba.nbaapp.core.presentation.UiState
import com.bumba.nbaapp.core.presentation.UiStateHandler
import com.bumba.nbaapp.domain.model.Game
import kotlinx.coroutines.launch

data class TeamBottomSheetState(
    val games: List<Game>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamBottomSheet(
    onDismiss: () -> Unit = {},
    sheetState: SheetState,
    teamName: String,
    uiState: UiState<TeamBottomSheetState>,
    onFetchData: () -> Unit
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        onFetchData()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        dragHandle = null,
    ) {
        TeamBottomSheetContent(
            teamName = teamName,
            uiState = uiState,
            onBackToHome = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        onDismiss()
                    }
                }
            },
            onGameClick = { },
            onRetry = onFetchData
        )
    }
}

@Composable
private fun TeamBottomSheetContent(
    teamName: String,
    uiState: UiState<TeamBottomSheetState>,
    onBackToHome: () -> Unit = {},
    onGameClick: (Game) -> Unit = {},
    onRetry: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 28.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .clickable(onClick = onBackToHome),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Back to Home",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = stringResource(R.string.home),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Text(
                text = teamName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(56.dp))
        Box(modifier = Modifier.fillMaxSize()) {
            UiStateHandler(
                uiState = uiState,
                onRetry = onRetry
            ) { teamBottomSheetState ->
                TeamBottomSheetContent(
                    uiState = teamBottomSheetState,
                    onGameClick = onGameClick
                )
            }
        }
    }
}

@Composable
private fun TeamBottomSheetContent(
    uiState: TeamBottomSheetState,
    onGameClick: (Game) -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Home\nName",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                lineHeight = 18.sp,
                modifier = Modifier.weight(1.15f)
            )
            Text(
                text = "Home\nScore",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                lineHeight = 18.sp,
                modifier = Modifier.weight(0.95f)
            )
            Text(
                text = "Visitor\nName",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                lineHeight = 18.sp,
                modifier = Modifier.weight(1.15f)
            )
            Text(
                text = "Visitor\nScore",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                lineHeight = 18.sp,
                modifier = Modifier.weight(0.95f)
            )
        }
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 16.dp),
            thickness = 1.dp,
            color = Color(0xFFCCCCCC)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(uiState.games) { game ->
                GameRow(
                    game = game,
                    onClick = { onGameClick(game) }
                )
            }
        }
    }
}

@Composable
fun GameRow(
    game: Game,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = formatTeamName(game.homeTeamName),
            fontSize = 16.sp,
            color = Color.Black,
            lineHeight = 22.sp,
            modifier = Modifier.weight(1.15f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = game.homeTeamScore,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(0.95f)
        )
        Text(
            text = formatTeamName(game.visitorTeamName),
            fontSize = 16.sp,
            color = Color.Black,
            lineHeight = 22.sp,
            modifier = Modifier.weight(1.15f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = game.visitorTeamScore,
            fontSize = 16.sp,
            color = Color.Black,
            modifier = Modifier.weight(0.95f)
        )
    }
}

private fun formatTeamName(name: String): String {
    val trimmed = name.trim()
    val spaceIndex = trimmed.indexOf(' ')
    return if (spaceIndex > 0 && !trimmed.contains('\n')) {
        trimmed.substring(0, spaceIndex) + "\n" + trimmed.substring(spaceIndex + 1)
    } else {
        trimmed
    }
}

@Preview
@Composable
private fun TeamScreenPreview() {
    TeamBottomSheetContent(
        teamName = "Atlanta Hawks",
        uiState = UiState.Success(
            TeamBottomSheetState(
                listOf(
                    Game(
                        homeTeamName = "Atlanta Hawks",
                        homeTeamScore = "103",
                        visitorTeamName = "Chicago Bulls",
                        visitorTeamScore = "103"
                    )
                )
            )
        )
    )
}
