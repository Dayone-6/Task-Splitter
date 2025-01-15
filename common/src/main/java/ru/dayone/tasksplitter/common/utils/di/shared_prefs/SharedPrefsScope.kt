package ru.dayone.tasksplitter.common.utils.di.shared_prefs

import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Singleton
@Retention(value = AnnotationRetention.RUNTIME)
annotation class SharedPrefsScope
