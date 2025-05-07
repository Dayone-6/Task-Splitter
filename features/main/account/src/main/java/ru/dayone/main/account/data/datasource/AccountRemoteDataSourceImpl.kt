package ru.dayone.main.account.data.datasource

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ru.dayone.main.account.data.network.AccountRetrofitService
import ru.dayone.main.account.data.network.models.UserFriend
import ru.dayone.main.account.domain.datasource.AccountRemoteDataSource
import ru.dayone.tasksplitter.common.exceptions.RequestCanceledException
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.USERS_FIRESTORE_COLLECTION
import ru.dayone.tasksplitter.common.utils.handle

class AccountRemoteDataSourceImpl(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val accountService: AccountRetrofitService
) : AccountRemoteDataSource {
    override suspend fun signOut(): Result<Unit> {
        return try {
            auth.signOut()
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }

    override suspend fun getPoints(id: String): Result<Int> {
        return Result.Success(0)
    }

    override suspend fun getFriends(id: String): Result<List<User>> {
        return try {
            val friendIdsResult = accountService.getUserFriends(id).handle()
            Log.d("AccountRemoteDataSource", friendIdsResult.toString())
            if (friendIdsResult is Result.Success) {
                val task = db.collection(USERS_FIRESTORE_COLLECTION)
                    .whereIn("id", friendIdsResult.result.map { it.friendId }).get()
                val taskResult = task.await()
                val result: Result<List<User>> = if (task.isSuccessful) {
                    Result.Success(taskResult.toObjects(User::class.java))
                } else if (task.isCanceled) {
                    Result.Error(RequestCanceledException())
                } else if (task.exception != null) {
                    Result.Error(task.exception!!)
                } else {
                    Result.Error(Exception())
                }
                return result
            } else {
                return Result.Error((friendIdsResult as Result.Error).exception)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }

    override suspend fun addFriend(userId: String, friendId: String): Result<UserFriend> {
        return try {
            return accountService.addFriend(userId, friendId).handle()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }

    override suspend fun getUsersByNickname(nickname: String): Result<List<User>> {
        return try {
            val task = db.collection(USERS_FIRESTORE_COLLECTION).get()
            val result = task.await()
            if (task.isSuccessful) {
                Result.Success(
                    result = result.toObjects<User>(User::class.java)
                        .filter { it.nickname!!.startsWith(nickname) }
                )
            } else if (task.isCanceled) {
                Result.Error(RequestCanceledException())
            } else {
                Result.Error(task.exception!!)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(e)
        }
    }
}