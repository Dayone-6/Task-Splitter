package ru.dayone.main.my_tasks.data.datasource

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import ru.dayone.main.my_tasks.data.network.TasksService
import ru.dayone.main.my_tasks.data.network.models.Group
import ru.dayone.main.my_tasks.data.network.models.Vote
import ru.dayone.main.my_tasks.data.network.models.VoteForTaskRequestBody
import ru.dayone.main.my_tasks.domain.datasource.TasksRemoteDataSource
import ru.dayone.tasksplitter.common.exceptions.RequestCanceledException
import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.USERS_FIRESTORE_COLLECTION
import ru.dayone.tasksplitter.common.utils.handle
import javax.inject.Inject

class TasksRemoteDataSourceImpl @Inject constructor(
    private val service: TasksService,
    private val db: FirebaseFirestore
) : TasksRemoteDataSource {
    override suspend fun voteForTask(
        taskId: String,
        vote: Int,
        userId: String
    ): Result<Vote> {
        return service.voteForTask(taskId, VoteForTaskRequestBody(userId, vote)).handle()
    }

    override suspend fun endTask(taskId: String): Result<Task> {
        return service.endTask(taskId).handle()
    }

    override suspend fun updateUserPoints(points: Int, user: User): Result<Unit> {
        val task = db.collection(USERS_FIRESTORE_COLLECTION).document(user.id)
            .update("points", user.points!! + points)
        val result = task.await()
        return if (task.isSuccessful) {
            Result.Success(Unit)
        } else if (task.exception != null) {
            Result.Error(task.exception!!)
        } else {
            Result.Error(RequestCanceledException())
        }
    }

    override suspend fun getVotes(taskId: String): Result<List<Vote>> {
        return service.getTaskVotes(taskId).handle()
    }

    override suspend fun getUser(userId: String): Result<User> {
        val task = db.collection(USERS_FIRESTORE_COLLECTION).document(userId).get()
        val result = task.await()
        return if (task.isSuccessful) {
            Result.Success(result.toObject(User::class.java)!!)
        } else if (task.isCanceled) {
            Result.Error(RequestCanceledException())
        } else {
            Result.Error(task.exception!!)
        }
    }

    override suspend fun getUserTasks(userId: String, isCompleted: Boolean): Result<List<Task>> {
        return if (isCompleted) {
            service.getUserCompletedTasks(userId)
        } else {
            service.getUserTasks(userId)
        }.handle()
    }

    override suspend fun getGroupById(groupId: String): Result<Group> {
        return service.getGroupById(groupId).handle()
    }

    override suspend fun payForTask(taskId: String): Result<Task> {
        return service.payForTask(taskId).handle()
    }
}