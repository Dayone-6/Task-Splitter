package ru.dayone.auth.data.datasource

import android.content.SharedPreferences
import ru.dayone.auth.domain.datasource.AuthLocalDataSource
import ru.dayone.auth.domain.model.User
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsScope
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val sharedPrefs: SharedPreferences
) : AuthLocalDataSource {

    override suspend fun saveCurrentUser(user: User) {

    }

    override suspend fun loadCurrentUser(): Result<User> {
        return Result.Error(Exception())
    }
}