package ru.dayone.main.account.data.datasource

import com.google.firebase.auth.FirebaseAuth
import ru.dayone.main.account.domain.datasource.AccountRemoteDataSource
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result

class AccountRemoteDataSourceImpl(
    val auth: FirebaseAuth
) : AccountRemoteDataSource {
    override suspend fun signOut(): Result<Unit> {
        return try {
            auth.signOut()
            Result.Success(Unit)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun getFriends(id: String): Result<List<User>> {
        return Result.Error(Exception())
    }
}