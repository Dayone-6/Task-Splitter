package ru.dayone.main.my_tasks.domain.datasource

import ru.dayone.main.my_tasks.data.network.models.Vote
import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result

interface TasksRemoteDataSource {
    suspend fun voteForTask(taskId: String, vote: Int, userId: String) : Result<Vote>
    suspend fun endTask(taskId: String) : Result<Task>
    suspend fun getVotes(taskId: String) : Result<List<Vote>>
    suspend fun getUser(userId: String) : Result<User>
}