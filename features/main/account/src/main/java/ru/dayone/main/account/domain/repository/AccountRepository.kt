package ru.dayone.main.account.domain.repository

import ru.dayone.main.account.data.network.models.friends.UserFriend
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result

interface AccountRepository {
    suspend fun signOut(): Result<Unit>
    suspend fun getPoints(id: String): Result<Int>
    suspend fun getFriends(id: String): Result<List<User>>
    suspend fun addFriend(friendId: String): Result<UserFriend>
    suspend fun getUser(): User?
}