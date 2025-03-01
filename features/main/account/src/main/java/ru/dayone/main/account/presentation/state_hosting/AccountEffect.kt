package ru.dayone.main.account.presentation.state_hosting

sealed class AccountEffect {
    class NavigateToSignIn : AccountEffect()
    class StartLoading : AccountEffect()
    class StopLoading : AccountEffect()
}