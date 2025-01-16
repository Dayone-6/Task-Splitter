package ru.dayone.taskssplitter.di

import dagger.Component
import ru.dayone.auth.data.di.AuthComponent
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsComponent
import javax.inject.Singleton

@Component
interface AppComponent {
    @Component.Factory
    interface Factory{
        fun create(): AppComponent
    }
    fun authComponentFactory() : AuthComponent.Factory

    fun sharedPrefsComponentFactory() : SharedPrefsComponent.Factory
}