package ru.dayone.auth.domain

sealed class AuthType {
    data class EmailAndPassword(
        val email: String,
        val password: String,
    ) : AuthType()

    data class Phone(
        val phoneNumber: String,
        val confirmationCode: String?
    ) : AuthType()

    class Google() : AuthType()
}