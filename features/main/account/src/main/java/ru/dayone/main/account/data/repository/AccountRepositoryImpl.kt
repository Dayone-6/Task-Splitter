package ru.dayone.main.account.data.repository

import ru.dayone.main.account.data.network.models.UserFriend
import ru.dayone.main.account.domain.datasource.AccountLocalDataSource
import ru.dayone.main.account.domain.datasource.AccountRemoteDataSource
import ru.dayone.main.account.domain.repository.AccountRepository
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val localDataSource: AccountLocalDataSource,
    private val remoteDataSource: AccountRemoteDataSource
) : AccountRepository {
    override suspend fun signOut(): Result<Unit> {
        return when (val remoteResult = remoteDataSource.signOut()) {
            is Result.Success -> {
                val localResult = localDataSource.deleteUser()
                localResult
            }

            is Result.Error -> {
                remoteResult
            }
        }
    }

    override suspend fun getPoints(id: String): Result<Int> {
        return when (val result = remoteDataSource.getPoints(id)) {
            is Result.Success -> {
                result
            }

            is Result.Error -> {
                result
            }
        }
    }

    override suspend fun getFriends(id: String): Result<List<User>> {
        return try {
            remoteDataSource.getFriends(id)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun addFriend(friendId: String): Result<UserFriend> {
        try {
            val userId = localDataSource.getUser()!!.id
            return remoteDataSource.addFriend(userId, friendId)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun getUser(): User? {
        return localDataSource.getUser()
    }

    override suspend fun getUsersByNickname(nickname: String): Result<List<User>> {
        return remoteDataSource.getUsersByNickname(nickname)
    }
}