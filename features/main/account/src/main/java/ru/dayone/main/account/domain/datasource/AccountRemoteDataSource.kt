package ru.dayone.main.account.domain.datasource

import ru.dayone.main.account.data.network.models.Points
import ru.dayone.main.account.data.network.models.UserFriend
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result

interface AccountRemoteDataSource{
    suspend fun signOut(): Result<Unit>
    suspend fun getFriends(id: String): Result<List<User>>
    suspend fun addFriend(userId: String, friendId: String): Result<UserFriend>
    suspend fun getUsersByNickname(nickname: String): Result<List<User>>
}