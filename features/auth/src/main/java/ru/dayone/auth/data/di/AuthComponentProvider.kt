package ru.dayone.auth.data.di

interface AuthComponentProvider {
    fun provideAuthComponent(): AuthComponent
}