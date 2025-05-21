package ru.dayone.main.my_tasks.domain.repository

import ru.dayone.main.my_tasks.data.models.VoteUI
import ru.dayone.main.my_tasks.data.network.models.Group
import ru.dayone.main.my_tasks.data.network.models.Vote
import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result

interface TasksRepository {
    suspend fun voteForTask(taskId: String, vote: Int) : Result<Vote>
    suspend fun endTask(taskId: String) : Result<Task>
    suspend fun getVotes(taskId: String) : Result<List<VoteUI>>
    suspend fun getCurrentUser() : Result<User>
    suspend fun getUser(userId: String) : Result<User>
    suspend fun getUserTasks(isCompleted: Boolean) : Result<List<Task>>
    suspend fun getGroupById(groupId: String) : Result<Group>
}