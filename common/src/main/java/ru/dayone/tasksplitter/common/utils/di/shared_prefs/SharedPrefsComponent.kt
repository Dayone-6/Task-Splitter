package ru.dayone.tasksplitter.common.utils.di.shared_prefs

import android.content.Context
import android.content.SharedPreferences
import dagger.BindsInstance
import dagger.Subcomponent


@Subcomponent(modules = [SharedPrefsModule::class])
@SharedPrefsScope
interface SharedPrefsComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(@BindsInstance context: Context) : SharedPrefsComponent
    }

    fun getSharedPrefs() : SharedPreferences
}