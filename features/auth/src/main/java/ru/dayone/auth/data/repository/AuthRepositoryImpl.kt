package ru.dayone.auth.data.repository

import ru.dayone.auth.data.AuthType
import ru.dayone.auth.domain.datasource.AuthLocalDataSource
import ru.dayone.auth.domain.datasource.AuthRemoteDataSource
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.auth.domain.repository.AuthRepository
import ru.dayone.tasksplitter.common.utils.Result

class AuthRepositoryImpl(
    private val localDataSource: AuthLocalDataSource,
    private val remoteDataSource: AuthRemoteDataSource
) : AuthRepository {
    override suspend fun signIn(type: AuthType): Result<User> {
        return try {
            when (val result = remoteDataSource.signIn(type)) {
                is Result.Success -> {
                    saveCurrentUser(result.result)
                    Result.Success(result.result)
                }

                is Result.Error -> {
                    Result.Error(result.exception)
                }
            }
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun signUp(name: String, nickname: String, color: Int): Result<User> {
        val currentUserResult = localDataSource.loadCurrentUser()
        if(currentUserResult is Result.Error){
            return currentUserResult
        }
        val currentUserId = (currentUserResult as Result.Success).result.id
        return when(val signUpResult = remoteDataSource.signUp(User(currentUserId, name, nickname, color))){
            is Result.Success -> {
                saveCurrentUser(signUpResult.result)
                Result.Success(signUpResult.result)
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