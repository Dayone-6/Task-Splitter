package ru.dayone.main.account.data.repository

import ru.dayone.main.account.data.datasource.AccountLocalDataSourceImpl
import ru.dayone.main.account.domain.datasource.AccountLocalDataSource
import ru.dayone.main.account.domain.datasource.AccountRemoteDataSource
import ru.dayone.main.account.domain.repository.AccountRepository
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    val localDataSource: AccountLocalDataSource,
    val remoteDataSource: AccountRemoteDataSource
) : AccountRepository{
    override suspend fun signOut(): Result<Unit> {
        return when(val remoteResult = remoteDataSource.signOut()){
            is Result.Success -> {
                val localResult = localDataSource.deleteUser()
                localResult
            }
            is Result.Error -> {
                remoteResult
            }
        }
    }

    override suspend fun getPoints(): Result<Int> {
        return Result.Error(Exception())
    }

    override suspend fun getFriends(id: String): Result<List<User>> {
        return Result.Error(Exception())
    }
}