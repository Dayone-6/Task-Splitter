package ru.dayone.tasksplitter.common.utils.di.shared_prefs

import android.content.Context
import android.content.SharedPreferences
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Singleton


@Subcomponent(modules = [SharedPrefsModule::class])
@Singleton
interface SharedPrefsComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): SharedPrefsComponent
    }

    @EncryptedSharedPrefsQualifier
    fun getEncryptedSharedPrefs(): SharedPreferences

    @SettingsSharedPrefsQualifier
    fun getSharedPrefs(): SharedPreferences
}