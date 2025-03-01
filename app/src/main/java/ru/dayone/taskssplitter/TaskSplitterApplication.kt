package ru.dayone.taskssplitter

import android.app.Application
import ru.dayone.auth.data.di.AuthComponent
import ru.dayone.auth.data.di.AuthComponentProvider
import ru.dayone.main.data.di.MainComponent
import ru.dayone.main.data.di.MainComponentProvider
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsComponent
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsComponentProvider
import ru.dayone.taskssplitter.di.AppComponent
import ru.dayone.taskssplitter.di.DaggerAppComponent


class TaskSplitterApplication : Application(), AuthComponentProvider, SharedPrefsComponentProvider, MainComponentProvider {
    private var _appComponent: AppComponent? = null
    private val appComponent get() = _appComponent!!

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.factory().create()
    }

    override fun provideAuthComponent() : AuthComponent {
        return appComponent.authComponentFactory().create(applicationContext)
    }

    override fun provideSharedPrefsComponent(): SharedPrefsComponent {
        return appComponent.sharedPrefsComponentFactory().create(applicationContext)
    }

    override fun provideMainComponent(): MainComponent {
        return appComponent.mainComponentFactory().create(applicationContext)
    }
}