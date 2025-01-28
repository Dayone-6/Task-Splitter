package ru.dayone.auth.data

import android.content.Context
import com.google.firebase.auth.PhoneAuthCredential

sealed class AuthType {
    data class EmailAndPassword(
        val email: String,
        val password: String,
    ) : AuthType()

    data class Phone(
        val credential: PhoneAuthCredential
    ) : AuthType()

    data class Google(
        val context: Context
    ) : AuthType()
}