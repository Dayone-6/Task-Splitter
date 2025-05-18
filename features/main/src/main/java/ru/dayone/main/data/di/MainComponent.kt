package ru.dayone.main.data.di

import android.content.Context
import android.content.SharedPreferences
import dagger.BindsInstance
import dagger.Subcomponent
import ru.dayone.main.account.data.di.AccountModule
import ru.dayone.main.account.presentation.account.AccountViewModel
import ru.dayone.main.account.presentation.completed_tasks.CompletedTasksViewModel
import ru.dayone.main.account.presentation.friends.FriendsViewModel
import ru.dayone.main.my_groups.data.di.GroupsModule
import ru.dayone.main.my_groups.presentation.group.GroupViewModel
import ru.dayone.main.my_groups.presentation.my_groups.MyGroupsViewModel
import ru.dayone.main.my_tasks.data.di.TasksModule
import ru.dayone.main.my_tasks.presentation.task.TaskViewModel
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SettingsSharedPrefsQualifier
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsModule
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [AccountModule::class, SharedPrefsModule::class, GroupsModule::class, TasksModule::class])
interface MainComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): MainComponent
    }

    fun getAccountViewModel(): AccountViewModel

    fun getFriendsViewModel(): FriendsViewModel

    fun getCompletedTasksViewModel(): CompletedTasksViewModel

    fun getMyGroupsViewModel(): MyGroupsViewModel

    fun getGroupViewModel(): GroupViewModel

    fun getTaskViewModel(): TaskViewModel

    @SettingsSharedPrefsQualifier
    fun getSettingsPrefs(): SharedPreferences
}