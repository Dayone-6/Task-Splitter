package ru.dayone.auth.presentation.state_hosting

import ru.dayone.auth.domain.model.User

sealed class AuthState {
    data class Content(
        var user: User? = null
    ) : AuthState()
}