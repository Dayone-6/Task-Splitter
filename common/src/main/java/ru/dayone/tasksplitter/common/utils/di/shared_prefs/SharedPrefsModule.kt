package ru.dayone.tasksplitter.common.utils.di.shared_prefs

import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides

@Module
class SharedPrefsModule{

    @Provides
    @SharedPrefsScope
    fun provideSharedPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
}