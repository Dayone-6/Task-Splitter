package ru.dayone.auth.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Subcomponent
import ru.dayone.auth.presentation.AuthViewModel
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance context: Context): AuthComponent
    }

    fun getAuthViewModel() : AuthViewModel
}