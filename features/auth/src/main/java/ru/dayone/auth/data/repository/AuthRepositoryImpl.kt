package ru.dayone.auth.data.repository

import ru.dayone.auth.domain.AuthType
import ru.dayone.auth.domain.datasource.AuthLocalDataSource
import ru.dayone.auth.domain.datasource.AuthRemoteDataSource
import ru.dayone.auth.domain.model.User
import ru.dayone.auth.domain.repository.AuthRepository
import ru.dayone.tasksplitter.common.utils.Result

class AuthRepositoryImpl(
    private val localDataSource: AuthLocalDataSource,
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun signIn(type: AuthType): Result<User> {
        return when(val user = remoteDataSource.signIn(type)){
            is Result.Success -> {
                Result.Success(user.result)
            }
            is Result.Error -> {
                Result.Error(user.exception)
            }
        }
    }

    override suspend fun signUp(user: User): Result<User> {
        return when(val signUpResult = remoteDataSource.signUp(user)){
            is Result.Success -> {
                saveCurrentUser(user)
                Result.Success(user)
            }

            is Result.Error -> {
                Result.Error(signUpResult.exception)
            }
        }
    }

    override suspend fun saveCurrentUser(user: User) {
        localDataSource.saveCurrentUser(user)
    }
}