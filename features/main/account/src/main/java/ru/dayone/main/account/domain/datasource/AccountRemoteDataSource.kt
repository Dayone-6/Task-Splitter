package ru.dayone.main.account.domain.datasource

import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result

interface AccountRemoteDataSource {
    suspend fun signOut(): Result<Unit>
    suspend fun getPoints(id: String): Result<Int>
    suspend fun getFriends(id: String): Result<List<User>>
}