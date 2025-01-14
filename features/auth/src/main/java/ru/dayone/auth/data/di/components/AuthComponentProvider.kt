package ru.dayone.auth.data.di.components

interface AuthComponentProvider {
    fun provideSignInComponent() : AuthComponent
}