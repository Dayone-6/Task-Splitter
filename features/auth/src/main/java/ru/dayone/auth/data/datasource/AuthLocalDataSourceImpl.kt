package ru.dayone.auth.data.datasource

import android.content.SharedPreferences
import ru.dayone.auth.domain.datasource.AuthLocalDataSource
import ru.dayone.auth.domain.model.User
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.USER_ID_KEY
import ru.dayone.tasksplitter.common.utils.USER_NAME_KEY
import ru.dayone.tasksplitter.common.utils.USER_NICKNAME_KEY
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    @EncryptedSharedPrefsQualifier private val sharedPrefs: SharedPreferences
) : AuthLocalDataSource {

    override suspend fun saveCurrentUser(user: User) {
        sharedPrefs.edit()
            .putString(USER_ID_KEY, user.id)
            .putString(USER_NAME_KEY, user.name)
            .putString(USER_NICKNAME_KEY, user.nickname)
            .commit()
    }

    override suspend fun loadCurrentUser(): Result<User> {
        return try {
            Result.Success(User(
                sharedPrefs.getString(USER_ID_KEY, null)!!,
                sharedPrefs.getString(USER_NAME_KEY, "")!!,
                sharedPrefs.getString(USER_NICKNAME_KEY, "")!!
            ))
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}