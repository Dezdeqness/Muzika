package com.dezdeqness.auth.presentation

data class AuthState(
    val state: ScreenState = ScreenState.Initial,
    val isLoading: Boolean = true,
)

enum class ScreenState {
    Initial,
    NotLoggedIn,
}
