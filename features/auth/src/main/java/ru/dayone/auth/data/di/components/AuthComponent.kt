package ru.dayone.auth.data.di.components

import dagger.Subcomponent


@Subcomponent
interface AuthComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create(): AuthComponent
    }
}