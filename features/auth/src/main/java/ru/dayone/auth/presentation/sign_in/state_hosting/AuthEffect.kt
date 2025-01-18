package ru.dayone.auth.presentation.sign_in.state_hosting

sealed class AuthEffect {
    data object StartLoading : AuthEffect()

    data object StopLoading : AuthEffect()

    data object ToMain : AuthEffect()

    data object ToSignUp : AuthEffect()
}