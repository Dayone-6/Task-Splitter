package ru.dayone.taskssplitter.di

import dagger.Component
import ru.dayone.auth.data.di.components.AuthComponent
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsComponent

@Component
interface AppComponent {
    @Component.Factory
    interface Factory{
        fun create(): AppComponent
    }
    fun authComponentFactory() : AuthComponent.Factory

    fun sharedPrefsComponentFactory() : SharedPrefsComponent.Factory
}