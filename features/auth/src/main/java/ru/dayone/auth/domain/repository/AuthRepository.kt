package ru.dayone.auth.domain.repository

import ru.dayone.auth.data.AuthType
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result

interface AuthRepository {
    suspend fun signIn(type: AuthType): Result<User>

    suspend fun signUp(name: String, nickname: String, color: Int): Result<User>

    suspend fun saveCurrentUser(user: User)
}