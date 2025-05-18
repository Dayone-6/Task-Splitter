package ru.dayone.main.my_tasks.data.repository

import ru.dayone.main.my_tasks.data.network.models.Vote
import ru.dayone.main.my_tasks.domain.datasource.TasksLocalDataSource
import ru.dayone.main.my_tasks.domain.datasource.TasksRemoteDataSource
import ru.dayone.main.my_tasks.domain.repository.TasksRepository
import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.tasksplitter.common.models.User
import javax.inject.Inject
import ru.dayone.tasksplitter.common.utils.Result

class TasksRepositoryImpl @Inject constructor(
    private val localDataSource: TasksLocalDataSource,
    private val remoteDataSource: TasksRemoteDataSource
): TasksRepository {
    override suspend fun voteForTask(
        taskId: String,
        vote: Int
    ): Result<Vote> {
        return try {
            val user = localDataSource.getCurrentUser()
            return if(user != null){
                remoteDataSource.voteForTask(taskId, vote, user.id)
            }else{
                Result.Error(NullPointerException())
            }
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun endTask(taskId: String): Result<Task> {
        return try {
            remoteDataSource.endTask(taskId)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun getVotes(taskId: String): Result<List<Vote>> {
        return try {
            remoteDataSource.getVotes(taskId)
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun getCurrentUser(): Result<User> {
        return try {
            val user = localDataSource.getCurrentUser()
            return if(user != null){
                Result.Success(user)
            }else{
                Result.Error(NullPointerException())
            }
        }catch (e: Exception){
            Result.Error(e)
        }
    }

    override suspend fun getUser(userId: String): Result<User> {
        return try {
            return remoteDataSource.getUser(userId)
        }catch (e: Exception){
            Result.Error(e)
        }
    }
}