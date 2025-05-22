package ru.dayone.auth.data.datasource

import android.annotation.SuppressLint
import android.content.SharedPreferences
import ru.dayone.auth.domain.datasource.AuthLocalDataSource
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.USER_COLOR_KEY
import ru.dayone.tasksplitter.common.utils.USER_ID_KEY
import ru.dayone.tasksplitter.common.utils.USER_NAME_KEY
import ru.dayone.tasksplitter.common.utils.USER_NICKNAME_KEY
import ru.dayone.tasksplitter.common.utils.USER_POINTS_KEY
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import ru.dayone.tasksplitter.common.utils.getUser
import javax.inject.Inject
import androidx.core.content.edit

class AuthLocalDataSourceImpl @Inject constructor(
    @EncryptedSharedPrefsQualifier private val sharedPrefs: SharedPreferences
) : AuthLocalDataSource {

    @SuppressLint("ApplySharedPref")
    override suspend fun saveCurrentUser(user: User) {
        sharedPrefs.edit(commit = true) {
            putString(USER_ID_KEY, user.id)
                .putString(USER_NAME_KEY, user.name)
                .putString(USER_NICKNAME_KEY, user.nickname)
                .putInt(USER_COLOR_KEY, user.color ?: 0)
                .putInt(USER_POINTS_KEY, user.points ?: 0)
        }
    }

    override suspend fun loadCurrentUser(): Result<User> {
        return try {
            val user = sharedPrefs.getUser() ?: throw NullPointerException()
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}