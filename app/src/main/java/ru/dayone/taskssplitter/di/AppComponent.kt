package ru.dayone.taskssplitter.di

import dagger.Component
import ru.dayone.auth.data.di.AuthComponent
import ru.dayone.main.data.di.MainComponent
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsComponent

@Component
interface AppComponent {
    @Component.Factory
    interface Factory{
        fun create(): AppComponent
    }
    fun authComponentFactory() : AuthComponent.Factory

    fun mainComponentFactory() : MainComponent.Factory

    fun sharedPrefsComponentFactory() : SharedPrefsComponent.Factory
}