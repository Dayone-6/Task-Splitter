package ru.dayone.main.account.presentation.account.state_hosting

sealed class AccountAction {
    class SignOut : AccountAction()
    class GetUser : AccountAction()
}