package ru.dayone.main.account.data.datasource

import android.content.SharedPreferences
import ru.dayone.main.account.domain.datasource.AccountLocalDataSource
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import javax.inject.Inject
import ru.dayone.tasksplitter.common.utils.Result


class AccountLocalDataSourceImpl @Inject constructor(
    @EncryptedSharedPrefsQualifier val sharedPreferences: SharedPreferences
) : AccountLocalDataSource {
    override suspend fun getUser(): User? {
        return null
    }

    override suspend fun deleteUser(): Result<Unit> {
        return Result.Error(Exception())
    }
}