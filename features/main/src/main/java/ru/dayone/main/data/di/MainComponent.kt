package ru.dayone.main.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Subcomponent
import ru.dayone.main.account.data.di.AccountModule
import ru.dayone.main.account.presentation.AccountViewModel
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [AccountModule::class])
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MainComponent
    }

    fun getAccountViewModel(): AccountViewModel
}