package ru.dayone.taskssplitter

import android.app.Application
import ru.dayone.auth.data.di.components.AuthComponent
import ru.dayone.auth.data.di.components.AuthComponentProvider
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsComponent
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsComponentProvider
import ru.dayone.taskssplitter.di.AppComponent
import ru.dayone.taskssplitter.di.DaggerAppComponent


class TaskSplitterApplication : Application(), AuthComponentProvider, SharedPrefsComponentProvider {
    private var _appComponent: AppComponent? = null
    private val appComponent get() = _appComponent!!

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.factory().create()
    }

    override fun provideSignInComponent() : AuthComponent {
        return appComponent.authComponentFactory().create()
    }

    override fun provideSharedPrefsComponent(): SharedPrefsComponent {
        return appComponent.sharedPrefsComponentFactory().create(applicationContext)
    }
}