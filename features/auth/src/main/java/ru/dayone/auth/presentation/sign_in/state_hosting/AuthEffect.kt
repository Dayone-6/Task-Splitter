package ru.dayone.auth.presentation.sign_in.state_hosting

sealed class AuthEffect {
    class Loading: AuthEffect()

    class ToMain: AuthEffect()

    class ToSignUp: AuthEffect()
}