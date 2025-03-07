package ru.dayone.main.account.presentation.account.state_hosting

sealed class AccountEffect {
    class NavigateToSignIn : AccountEffect()
    class StartLoading : AccountEffect()
    class StopLoading : AccountEffect()
}