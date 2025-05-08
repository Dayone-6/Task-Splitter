package ru.dayone.main.my_tasks.data.datasource

import android.content.SharedPreferences
import ru.dayone.main.my_tasks.domain.datasource.TasksLocalDataSource
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import javax.inject.Inject

class TasksLocalDataSourceImpl @Inject constructor(
    @EncryptedSharedPrefsQualifier private val sharedPrefs: SharedPreferences
) : TasksLocalDataSource {
}