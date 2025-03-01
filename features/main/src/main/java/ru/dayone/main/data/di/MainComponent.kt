package ru.dayone.main.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.BindsInstance
import dagger.Subcomponent
import ru.dayone.main.account.data.di.AccountModule
import ru.dayone.main.account.presentation.AccountViewModel
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [AccountModule::class])
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MainComponent
    }

    @EncryptedSharedPrefsQualifier
    fun getEncryptedSharedPreferences() : SharedPreferences

    fun getAccountViewModel() : AccountViewModel
}