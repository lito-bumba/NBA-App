package com.bumba.nbaapp.core.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumba.nbaapp.R

@Composable
fun GenericErrorCard(
    modifier: Modifier = Modifier,
    isInternetError: Boolean = false,
    message: String? = null,
    onRetry: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = if (isInternetError) Icons.Default.WifiOff else Icons.Default.Error,
            contentDescription = stringResource(R.string.error_icon),
            tint = Color.Red,
            modifier = Modifier.size(96.dp),
        )
        Spacer(Modifier.height(16.dp))
        val errorRes = if (isInternetError) R.string.internet_error_message
        else R.string.unknown_error_message
        val defaultMessage = stringResource(errorRes)
        val errorMessage = when {
            isInternetError -> defaultMessage
            !message.isNullOrBlank() -> "$defaultMessage\n\n$message"
            else -> defaultMessage
        }
        Text(
            text = errorMessage,
            color = Color.Red,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(),
        ) {
            Text(
                text = stringResource(R.string.try_again),
                color = Color.White,
            )
        }
    }
}

@Preview
@Composable
private fun InternetErrorPreview() {
    GenericErrorCard(isInternetError = true)
}

@Preview
@Composable
private fun UnknownErrorPreview() {
    GenericErrorCard(isInternetError = false)
}

@Preview
@Composable
private fun UnknownErrorWithMessagePreview() {
    GenericErrorCard(
        isInternetError = false,
        message = "Server error 500",
    )
}
