package ru.dayone.auth.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Subcomponent
import ru.dayone.auth.presentation.sign_in.AuthViewModel
import ru.dayone.auth.presentation.sign_up.SignUpViewModel
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [AuthModule::class])
interface AuthComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AuthComponent
    }

    fun getAuthViewModel(): AuthViewModel

    fun getSignUpViewModel(): SignUpViewModel
}