package com.bumba.nbaapp.core.presentation

import java.io.IOException
import java.nio.channels.UnresolvedAddressException

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Error(
        val isInternetError: Boolean,
        val message: String? = null,
    ) : UiState<Nothing>

    data class Success<T>(val data: T) : UiState<T>
}

fun Throwable.isInternetError(): Boolean {
    var current: Throwable? = this
    while (current != null) {
        if ((current is IOException) || (current is UnresolvedAddressException)) {
            return true
        }
        val msg = current.message?.lowercase() ?: ""
        if (msg.contains("unable to resolve host") ||
            msg.contains("no address associated with hostname") ||
            msg.contains("failed to connect") ||
            msg.contains("network error") ||
            msg.contains("connection error") ||
            msg.contains("connection refused") ||
            msg.contains("connection reset")
        ) {
            return true
        }
        current = current.cause
    }
    return false
}

fun Throwable.toErrorUiState(): UiState.Error = UiState.Error(
    isInternetError = isInternetError(),
    message = localizedMessage,
)
