package ru.dayone.auth.domain

import com.google.firebase.auth.PhoneAuthCredential

sealed class AuthType {
    data class EmailAndPassword(
        val email: String,
        val password: String,
    ) : AuthType()

    data class Phone(
        val credential: PhoneAuthCredential
    ) : AuthType()

    class Google() : AuthType()
}