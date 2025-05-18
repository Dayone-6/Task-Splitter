package ru.dayone.main.my_tasks.data.datasource

import android.content.SharedPreferences
import ru.dayone.main.my_tasks.domain.datasource.TasksLocalDataSource
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import ru.dayone.tasksplitter.common.utils.getUser
import javax.inject.Inject

class TasksLocalDataSourceImpl @Inject constructor(
    @EncryptedSharedPrefsQualifier private val sharedPrefs: SharedPreferences
) : TasksLocalDataSource {
    override suspend fun getCurrentUser(): User? {
        return sharedPrefs.getUser()
    }
}