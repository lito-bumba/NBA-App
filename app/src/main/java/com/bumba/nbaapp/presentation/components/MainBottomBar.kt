package com.bumba.nbaapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumba.nbaapp.presentation.MainTab

@Composable
fun MainBottomBar(
    currentTab: MainTab,
    onTabSelected: (MainTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .navigationBarsPadding()
    ) {
        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xFFDCDCDC)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTabSelected(MainTab.HOME) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Home",
                    fontSize = 16.sp,
                    fontWeight = if (currentTab == MainTab.HOME) FontWeight.Bold else FontWeight.Normal,
                    color = Color.Black
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onTabSelected(MainTab.PLAYERS) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Players",
                    fontSize = 16.sp,
                    fontWeight = if (currentTab == MainTab.PLAYERS) FontWeight.Bold else FontWeight.Normal,
                    color = Color.Black
                )
            }
        }
    }
}
