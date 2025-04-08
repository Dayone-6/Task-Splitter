package ru.dayone.main.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.BindsInstance
import dagger.Subcomponent
import ru.dayone.main.account.data.di.AccountModule
import ru.dayone.main.account.presentation.account.AccountViewModel
import ru.dayone.main.account.presentation.completed_tasks.CompletedTasksViewModel
import ru.dayone.main.account.presentation.friends.FriendsViewModel
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SettingsSharedPrefsQualifier
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsModule
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [AccountModule::class, SharedPrefsModule::class])
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MainComponent
    }

    fun getAccountViewModel(): AccountViewModel

    fun getFriendsViewModel(): FriendsViewModel

    fun getCompletedTasksViewModel(): CompletedTasksViewModel

    @SettingsSharedPrefsQualifier
    fun getSettingsPrefs(): SharedPreferences
}