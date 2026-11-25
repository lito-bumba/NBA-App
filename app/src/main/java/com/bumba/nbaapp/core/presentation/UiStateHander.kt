package com.bumba.nbaapp.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun <T> UiStateHandler(
    modifier: Modifier = Modifier,
    uiState: UiState<T>,
    onRetry: () -> Unit = {},
    successContent: @Composable (data: T) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        when (uiState) {
            UiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(64.dp),
                    strokeWidth = 8.dp
                )
            }

            is UiState.Error -> {
                GenericErrorCard(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = 16.dp),
                    isInternetError = uiState.isInternetError,
                    message = uiState.message,
                    onRetry = onRetry
                )
            }

            is UiState.Success -> {
                successContent(uiState.data)
            }
        }
    }
}


@Preview
@Composable
private fun HomeScreenLoadingPreview() {
    UiStateHandler(
        uiState = UiState.Loading,
        successContent = {}
    )
}

@Preview
@Composable
private fun HomeScreenInternetErrorPreview() {
    UiStateHandler(
        uiState = UiState.Error(isInternetError = true),
        successContent = {}
    )
}

@Preview
@Composable
private fun HomeScreenUnknownErrorPreview() {
    UiStateHandler(
        uiState = UiState.Error(
            isInternetError = false,
            message = "Unknown error message"
        ),
        successContent = {}
    )
}