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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumba.nbaapp.domain.model.Team
import com.bumba.nbaapp.presentation.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier,
    onTeamClick: (Team) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()

    HomeScreen(
        modifier = modifier,
        teams = uiState.teams,
        selectedSort = uiState.selectedSort,
        onSortClick = viewModel::onSortClick,
        onTeamClick = onTeamClick
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    teams: List<Team> = listOf(
        Team(
            id = "1",
            conference = "East",
            division = "Southeast",
            city = "Atlanta",
            name = "Atlanta Hawks",
            abbreviation = "ATL"
        )
    ),
    selectedSort: String = "Name",
    onSortClick: () -> Unit = {},
    onTeamClick: (Team) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Home",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFFD3D3D3))
                    .clickable { onSortClick() }
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = selectedSort,
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
                text = "Name",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1.3f)
            )
            Text(
                text = "City",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1.0f)
            )
            Text(
                text = "Conference",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.weight(1.1f)
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth(0.92f)
                .padding(top = 10.dp, bottom = 14.dp),
            thickness = 1.dp,
            color = Color(0xFFCCCCCC)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(teams) { team ->
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
                        .padding(top = 36.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun TeamRow(
    team: Team,
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
            text = team.name,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.weight(1.3f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = team.city,
            fontSize = 14.sp,
            color = Color.Black,
            modifier = Modifier.weight(1.0f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
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

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
private fun HomeScreenPreview() {
    HomeScreen()
}
