package ru.dayone.taskssplitter.di

import dagger.Component
import ru.dayone.auth.data.di.components.AuthComponent

@Component
interface AppComponent {
    @Component.Factory
    interface Factory{
        fun create(): AppComponent
    }
    fun signInComponentFactory() : AuthComponent.Factory
}