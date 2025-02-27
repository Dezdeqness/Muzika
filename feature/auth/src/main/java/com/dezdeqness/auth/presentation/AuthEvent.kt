package com.dezdeqness.auth.presentation

sealed class AuthEvent {
    data class OpenUrl(val url: String) : AuthEvent()
    data object NavigateMainFlow : AuthEvent()
}
