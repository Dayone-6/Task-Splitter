package ru.dayone.auth.presentation.sign_up.state_hosting

sealed class SignUpEffect {
    class ToMain : SignUpEffect()

    class StartLoading : SignUpEffect()

    class StopLoading : SignUpEffect()
}