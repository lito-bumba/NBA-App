package com.bumba.nbaapp.presentation.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumba.nbaapp.core.presentation.UiState
import com.bumba.nbaapp.core.presentation.UiStateHandler
import com.bumba.nbaapp.domain.model.Player

data class PlayerTabState(
    val players: List<Player>,
    val selectedSort: String
)

@Composable
fun PlayerScreen(
    uiState: UiState<PlayerTabState>,
    onPlayerClick: (Player) -> Unit = {},
    onFetchData: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        onFetchData()
    }

    UiStateHandler(
        uiState = uiState,
        onRetry = onFetchData
    ) { uiState ->
        PlayerScreen(
            players = uiState.players,
            onPlayerClick = onPlayerClick
        )
    }
}

@Composable
private fun PlayerScreen(
    players: List<Player>,
    onPlayerClick: (Player) -> Unit = {}
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val filteredPlayers = remember(players, searchQuery) {
        if (searchQuery.isBlank()) {
            players
        } else {
            players.filter {
                it.firstName.contains(searchQuery, ignoreCase = true) ||
                        it.lastName.contains(searchQuery, ignoreCase = true) ||
                        it.teamName.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "Players",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(20.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(CircleShape)
                .background(Color(0xFFD3D3D3)),
            contentAlignment = Alignment.Center
        ) {
            BasicTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 16.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (searchQuery.isEmpty()) {
                            Text(
                                text = "Search",
                                fontSize = 16.sp,
                                color = Color(0xFF222222),
                                textAlign = TextAlign.Center
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
        Spacer(modifier = Modifier.height(28.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "First Name",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(0.95f)
            )
            Text(
                text = "Last Name",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(0.95f)
            )
            Text(
                text = "Team",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1.6f)
            )
        }
        HorizontalDivider(
            modifier = Modifier.padding(top = 10.dp, bottom = 14.dp),
            thickness = 1.dp,
            color = Color(0xFFCCCCCC)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(filteredPlayers) { player ->
                PlayerRow(
                    player = player,
                    onClick = { onPlayerClick(player) }
                )
            }
            item {
                Text(
                    text = "...",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun PlayerRow(
    player: Player,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = player.firstName,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.weight(0.95f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = player.lastName,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.weight(0.95f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row(
            modifier = Modifier.weight(1.6f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = player.teamName,
                fontSize = 14.sp,
                color = Color.Black,
                modifier = Modifier.weight(1f, fill = false),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PlayerScreenPreview() {
    PlayerScreen(
        UiState.Success(
            PlayerTabState(
                players = listOf(
                    element = Player(
                        firstName = "Žydrūnas",
                        lastName = "Ilgauskas",
                        teamName = "Cleavland Cavaliers"
                    )
                ),
                selectedSort = ""
            )
        )
    )
}
