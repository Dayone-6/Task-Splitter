package ru.dayone.main.my_groups.data.repository

import ru.dayone.main.my_groups.domain.datasource.GroupsLocalDataSource
import ru.dayone.main.my_groups.domain.datasource.GroupsRemoteDataSource
import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.main.my_groups.data.network.models.GroupMember
import ru.dayone.main.my_groups.data.network.models.Task
import ru.dayone.main.my_groups.domain.repository.GroupsRepository
import ru.dayone.tasksplitter.common.utils.Result
import javax.inject.Inject

class GroupsRepositoryImpl @Inject constructor(
    private val localDataSource: GroupsLocalDataSource,
    private val remoteDataSource: GroupsRemoteDataSource
) : GroupsRepository {
    override suspend fun getMyGroups(requireNew: Boolean): Result<List<Group>> {
        try {
            val user = localDataSource.getUser()
            if(user == null){
                throw Exception()
            }
            return remoteDataSource.getGroups(user.id, requireNew)
        }catch (e: Exception){
            return Result.Error(e)
        }
    }

    override suspend fun createGroup(name: String): Result<Group> {
        try {
            val user = localDataSource.getUser()
            if(user == null){
                throw Exception()
            }
            return remoteDataSource.createGroup(user.id, name)
        }catch (e: Exception){
            return Result.Error(e)
        }
    }

    override suspend fun getGroupTasks(groupId: String, requireNew: Boolean): Result<List<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun addMemberToGroup(
        groupId: String,
        memberId: String
    ): Result<GroupMember> {
        TODO("Not yet implemented")
    }
}