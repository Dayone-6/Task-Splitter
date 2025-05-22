package ru.dayone.main.account.data.datasource

import android.content.SharedPreferences
import ru.dayone.main.account.domain.datasource.AccountLocalDataSource
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


class AccountLocalDataSourceImpl @Inject constructor(
    @EncryptedSharedPrefsQualifier val sharedPreferences: SharedPreferences
) : AccountLocalDataSource {
    override suspend fun getUser(): User? {
        return try {
            sharedPreferences.getUser()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun deleteUser(): Result<Unit> {
        return try {
            sharedPreferences.edit(commit = true) {
                remove(USER_ID_KEY)
                remove(USER_COLOR_KEY)
                remove(USER_NAME_KEY)
                remove(USER_NICKNAME_KEY)
                remove(USER_POINTS_KEY)
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}