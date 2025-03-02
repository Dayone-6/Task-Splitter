package ru.dayone.main.account.presentation.state_hosting

sealed class AccountAction {
    class SignOut : AccountAction()
    data class RequestPoints(val userId: String) : AccountAction()
    class GetUser : AccountAction()
}