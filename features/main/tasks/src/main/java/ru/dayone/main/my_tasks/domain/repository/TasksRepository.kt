package ru.dayone.main.my_tasks.domain.repository

import ru.dayone.main.my_tasks.data.network.models.Vote
import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result

interface TasksRepository {
    suspend fun voteForTask(taskId: String, vote: Int) : Result<Vote>
    suspend fun endTask(taskId: String) : Result<Task>
    suspend fun getVotes(taskId: String) : Result<List<Vote>>
    suspend fun getCurrentUser() : Result<User>
    suspend fun getUser(userId: String) : Result<User>
}