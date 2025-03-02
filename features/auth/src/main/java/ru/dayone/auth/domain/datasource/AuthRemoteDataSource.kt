package ru.dayone.auth.domain.datasource

import ru.dayone.auth.data.AuthType
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result

interface AuthRemoteDataSource {
    suspend fun signIn(type: AuthType): Result<User>

    suspend fun signUp(user: User): Result<User>
}