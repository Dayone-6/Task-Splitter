package ru.dayone.auth.domain.repository

import ru.dayone.auth.data.AuthType
import ru.dayone.auth.domain.model.User
import ru.dayone.tasksplitter.common.utils.Result

interface AuthRepository {
    suspend fun signIn(type: AuthType): Result<User>

    suspend fun signUp(user: User): Result<User>

    suspend fun saveCurrentUser(user: User)
}