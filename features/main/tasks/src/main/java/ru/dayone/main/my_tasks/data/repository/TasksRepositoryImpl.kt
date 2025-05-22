package ru.dayone.main.my_tasks.data.repository

import ru.dayone.main.my_tasks.data.models.VoteUI
import ru.dayone.main.my_tasks.data.network.models.Group
import ru.dayone.main.my_tasks.data.network.models.Vote
import ru.dayone.main.my_tasks.domain.datasource.TasksLocalDataSource
import ru.dayone.main.my_tasks.domain.datasource.TasksRemoteDataSource
import ru.dayone.main.my_tasks.domain.repository.TasksRepository
import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val localDataSource: TasksLocalDataSource,
    private val remoteDataSource: TasksRemoteDataSource
) : TasksRepository {
    override suspend fun voteForTask(
        taskId: String,
        vote: Int
    ): Result<Vote> {
        return try {
            val user = localDataSource.getCurrentUser()
            return if (user != null) {
                remoteDataSource.voteForTask(taskId, vote, user.id)
            } else {
                Result.Error(NullPointerException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun endTask(taskId: String): Result<Task> {
        return try {
            val result = remoteDataSource.endTask(taskId)
            if (result is Result.Success) {
                val user = localDataSource.getCurrentUser()
                val votesResult = remoteDataSource.getVotes(taskId)
                if (user != null) {
                    if (votesResult is Result.Success) {
                        val points = votesResult.result.find { it.userId == user.id }!!.vote
                        val updateResult = remoteDataSource.updateUserPoints(
                            points,
                            user
                        )
                        localDataSource.updateUserPoints(points)
                        if (updateResult is Result.Success) {
                            return Result.Success(result.result)
                        }
                        return Result.Error((updateResult as Result.Error).exception)
                    } else {
                        return Result.Error((votesResult as Result.Error).exception)
                    }
                } else {
                    return Result.Error(NullPointerException())
                }
            }
            return result
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getVotes(taskId: String): Result<List<VoteUI>> {
        return try {
            val result = remoteDataSource.getVotes(taskId)
            when (result) {
                is Result.Success -> {
                    val votesUi = mutableListOf<VoteUI>()
                    for (vote in result.result) {
                        val userResult = remoteDataSource.getUser(vote.userId)
                        if (userResult is Result.Success) {
                            votesUi.add(VoteUI(userResult.result, vote.vote))
                        } else {
                            return Result.Error((userResult as Result.Error).exception)
                        }
                    }
                    return Result.Success(votesUi)
                }

                is Result.Error -> {
                    return Result.Error(result.exception)
                }
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCurrentUser(): Result<User> {
        return try {
            val user = localDataSource.getCurrentUser()
            return if (user != null) {
                Result.Success(user)
            } else {
                Result.Error(NullPointerException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getUser(userId: String): Result<User> {
        return try {
            return remoteDataSource.getUser(userId)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getUserTasks(isCompleted: Boolean): Result<List<Task>> {
        return try {
            val user = localDataSource.getCurrentUser()
            if (user != null) {
                remoteDataSource.getUserTasks(user.id, isCompleted)
            } else {
                Result.Error(NullPointerException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getGroupById(groupId: String): Result<Group> {
        return try {
            remoteDataSource.getGroupById(groupId)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}