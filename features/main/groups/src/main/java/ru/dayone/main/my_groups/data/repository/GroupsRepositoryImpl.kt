package ru.dayone.main.my_groups.data.repository

import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.main.my_groups.data.network.models.GroupMember
import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.main.my_groups.domain.datasource.GroupsLocalDataSource
import ru.dayone.main.my_groups.domain.datasource.GroupsRemoteDataSource
import ru.dayone.main.my_groups.domain.repository.GroupsRepository
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result
import javax.inject.Inject

class GroupsRepositoryImpl @Inject constructor(
    private val localDataSource: GroupsLocalDataSource,
    private val remoteDataSource: GroupsRemoteDataSource
) : GroupsRepository {
    override suspend fun getMyGroups(requireNew: Boolean): Result<List<Group>> {
        try {
            val user = localDataSource.getUser()
            if (user == null) {
                throw Exception()
            }
            return remoteDataSource.getGroups(user.id, requireNew)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun createGroup(name: String): Result<Group> {
        try {
            val user = localDataSource.getUser()
            if (user == null) {
                throw Exception()
            }
            return remoteDataSource.createGroup(user.id, name)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun getGroupTasks(groupId: String, requireNew: Boolean): Result<List<Task>> {
        return try {
            remoteDataSource.getGroupTasks(groupId, requireNew)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun addMemberToGroup(
        groupId: String,
        memberId: String
    ): Result<GroupMember> {
        return try {
            remoteDataSource.addMemberToGroup(groupId, memberId)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getGroupMembers(groupId: String): Result<List<User>> {
        try {
            val groupMembersResult = remoteDataSource.getGroupMembers(groupId)
            if (groupMembersResult is Result.Success) {
                val groupMembers = groupMembersResult.result
                val users = mutableListOf<User>()
                for (groupMember in groupMembers) {
                    val result = remoteDataSource.getUserFromGroupMember(groupMember)
                    if (result is Result.Success) {
                        users.add(result.result)
                    } else {
                        throw (result as Result.Error).exception
                    }
                }
                return Result.Success(users)
            } else {
                return Result.Error((groupMembersResult as Result.Error).exception)
            }
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

    override suspend fun getUserFriends(): Result<List<User>> {
        return try {
            val user = localDataSource.getUser()
            if (user != null) {
                remoteDataSource.getUserFriends(user.id)
            } else {
                Result.Error(Exception())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun getCurrentUser(): Result<User> {
        return try {
            val user = localDataSource.getUser()
            return if (user != null) {
                Result.Success(user)
            } else {
                Result.Error(NullPointerException())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun createTask(
        groupId: String,
        title: String,
        description: String
    ): Result<Task> {
        return try {
            remoteDataSource.createTask(groupId, title, description)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}