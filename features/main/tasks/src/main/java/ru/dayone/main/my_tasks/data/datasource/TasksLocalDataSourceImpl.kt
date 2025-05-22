package ru.dayone.main.my_tasks.data.datasource

import android.content.SharedPreferences
import androidx.core.content.edit
import ru.dayone.main.my_tasks.domain.datasource.TasksLocalDataSource
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.USER_POINTS_KEY
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import ru.dayone.tasksplitter.common.utils.getUser
import javax.inject.Inject

class TasksLocalDataSourceImpl @Inject constructor(
    @EncryptedSharedPrefsQualifier private val sharedPrefs: SharedPreferences
) : TasksLocalDataSource {
    override suspend fun getCurrentUser(): User? {
        return sharedPrefs.getUser()
    }

    override suspend fun updateUserPoints(points: Int){
        sharedPrefs.edit(commit = true) {
            putInt(USER_POINTS_KEY, sharedPrefs.getInt(USER_POINTS_KEY, 0) + points)
        }
    }
}