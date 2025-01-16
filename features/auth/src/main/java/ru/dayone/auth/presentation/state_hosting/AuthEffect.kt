package ru.dayone.auth.presentation.state_hosting

sealed class AuthEffect {
    class Loading: AuthEffect()

    class ToMain: AuthEffect()

    class ToSignUp: AuthEffect()
}