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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumba.nbaapp.R
import com.bumba.nbaapp.domain.model.Team
import com.bumba.nbaapp.presentation.components.GenericErrorCard

sealed interface HomeTabState {
    data object Loading : HomeTabState
    data class Error(val isInternetError: Boolean) : HomeTabState
    data class Success(
        val teams: List<Team>,
        val selectedSort: String
    ) : HomeTabState
}

@Composable
fun HomeScreen(
    uiState: HomeTabState,
    onSortClick: (String) -> Unit = {},
    onTeamClick: (Team) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when (uiState) {
            HomeTabState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(64.dp),
                    strokeWidth = 8.dp
                )
            }

            is HomeTabState.Error -> GenericErrorCard(
                modifier = Modifier.align(Alignment.Center),
                isInternetError = uiState.isInternetError,
                onRetry = {}
            )

            is HomeTabState.Success -> HomeTabSuccessContent(
                onSortClick = onSortClick,
                uiState = uiState,
                onTeamClick = onTeamClick
            )
        }
    }
}

@Composable
private fun HomeTabSuccessContent(
    uiState: HomeTabState.Success,
    onSortClick: (String) -> Unit,
    onTeamClick: (Team) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.home),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFD3D3D3))
                    .clickable { onSortClick(uiState.selectedSort) }
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.selectedSort,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(36.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.name),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1.3f)
            )
            Text(
                text = stringResource(R.string.city),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1.0f)
            )
            Text(
                text = stringResource(R.string.conference),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1.1f)
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 12.dp),
            thickness = 1.dp,
            color = Color(0xFFCCCCCC)
        )

        LazyColumn(Modifier.fillMaxWidth()) {
            items(uiState.teams) { team ->
                TeamRow(
                    team = team,
                    onClick = { onTeamClick(team) }
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
                        .padding(top = 32.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun TeamRow(
    team: Team,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = team.name,
            fontSize = 14.sp,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1.3f)
        )
        Text(
            text = team.city,
            fontSize = 14.sp,
            color = Color.Black,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1.0f)
        )
        Row(
            modifier = Modifier.weight(1.1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = team.conference,
                fontSize = 14.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenLoadingPreview() {
    HomeScreen(HomeTabState.Loading)
}

@Preview
@Composable
private fun HomeScreenInternetErrorPreview() {
    HomeScreen(HomeTabState.Error(true))
}

@Preview
@Composable
private fun HomeScreenUnknownErrorPreview() {
    HomeScreen(HomeTabState.Error(false))
}

@Preview
@Composable
private fun HomeScreenSuccessPreview() {
    HomeScreen(
        HomeTabState.Success(
            teams = listOf(
                Team(
                    id = "1",
                    conference = "East",
                    division = "Southeast",
                    city = "Atlanta",
                    name = "Atlanta Hawks",
                    abbreviation = "ATL"
                )
            ),
            selectedSort = "Name"
        )
    )
}
