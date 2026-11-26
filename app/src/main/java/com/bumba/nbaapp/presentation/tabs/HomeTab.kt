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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumba.nbaapp.R
import com.bumba.nbaapp.core.presentation.UiState
import com.bumba.nbaapp.core.presentation.UiStateHandler
import com.bumba.nbaapp.domain.model.Team

data class HomeTabState(
    val teams: List<Team>,
    val selectedSort: String
)

@Composable
fun HomeScreen(
    uiState: UiState<HomeTabState>,
    onSortClick: (String) -> Unit = {},
    onTeamClick: (Team) -> Unit = {},
    onFetchData: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        onFetchData()
    }
    UiStateHandler(
        uiState = uiState,
        onRetry = onFetchData
    ) { homeData ->
        HomeTabSuccessContent(
            uiState = homeData,
            onSortClick = onSortClick,
            onTeamClick = onTeamClick
        )
    }
}

@Composable
private fun HomeTabSuccessContent(
    uiState: HomeTabState,
    onSortClick: (String) -> Unit,
    onTeamClick: (Team) -> Unit
) {
    var showSortDialog by rememberSaveable { mutableStateOf(false) }

    if (showSortDialog) {
        SortDialog(
            currentSort = uiState.selectedSort,
            onSortSelected = { selected ->
                onSortClick(selected)
                showSortDialog = false
            },
            onDismiss = { showSortDialog = false }
        )
    }

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
                color = Color.Black,
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFD3D3D3))
                    .clickable { showSortDialog = true }
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

@Composable
private fun SortDialog(
    currentSort: String,
    onSortSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val sortOptions = listOf("Name", "City", "Conference")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Sort by",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                sortOptions.forEach { option ->
                    val isSelected = option.equals(currentSort, ignoreCase = true)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSortSelected(option) }
                            .padding(vertical = 12.dp, horizontal = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = option,
                            fontSize = 16.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = Color.Black
                        )
                        RadioButton(
                            selected = isSelected,
                            onClick = { onSortSelected(option) },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color.Black,
                                unselectedColor = Color.Gray
                            )
                        )
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        containerColor = Color.White
    )
}

@Preview
@Composable
private fun HomeScreenSuccessPreview() {
    HomeScreen(
        uiState = UiState.Success(
            data = HomeTabState(
                teams = listOf(
                    Team(
                        id = "1",
                        conference = "East",
                        city = "Atlanta",
                        name = "Atlanta Hawks",
                    )
                ),
                selectedSort = "Name"
            )
        )
    )
}
