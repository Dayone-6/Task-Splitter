package ru.dayone.tasksplitter.common.utils.di.shared_prefs

interface SharedPrefsComponentProvider {
    fun provideSharedPrefsComponent(): SharedPrefsComponent
}