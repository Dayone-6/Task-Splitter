package ru.dayone.auth.presentation.sign_in.state_hosting

import ru.dayone.auth.data.AuthType

sealed class AuthAction {
    data class SignInUser(val authType: AuthType) : AuthAction()

    data class OnPasswordChanged(val password: String) : AuthAction()
}