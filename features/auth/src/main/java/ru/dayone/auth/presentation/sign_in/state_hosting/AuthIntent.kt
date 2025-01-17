package ru.dayone.auth.presentation.sign_in.state_hosting

import ru.dayone.auth.domain.AuthType

sealed class AuthIntent {
    data class SignInUser(val authType: AuthType) : AuthIntent()

    data class OnPasswordChanged(val password: String) : AuthIntent()
}