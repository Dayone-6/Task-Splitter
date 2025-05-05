package ru.dayone.main.my_groups.data.datasource

import ru.dayone.main.my_groups.data.network.GroupsRetrofitService
import ru.dayone.main.my_groups.data.network.models.AddMemberToGroupRequestBody
import ru.dayone.main.my_groups.data.network.models.CreateGroupRequestBody
import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.main.my_groups.data.network.models.GroupMember
import ru.dayone.main.my_groups.data.network.models.Task
import ru.dayone.main.my_groups.domain.datasource.GroupsRemoteDataSource
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.handle
import javax.inject.Inject

class GroupsRemoteDataSourceImpl @Inject constructor(
    private val service: GroupsRetrofitService
) : GroupsRemoteDataSource {
    private var cachedGroups: List<Group>? = null

    private var cachedGroupTasks: HashMap<String, List<Task>> = hashMapOf<String, List<Task>>()

    override suspend fun getGroups(userId: String, requireNew: Boolean): Result<List<Group>> {
        if(cachedGroups == null || requireNew) {
            val result = service.getUserGroups(userId).handle()
            if(result is Result.Success){
                cachedGroups = result.result
            }
            return result
        }else{
            return Result.Success(cachedGroups!!)
        }
    }

    override suspend fun createGroup(
        userId: String,
        name: String
    ): Result<Group> {
        return service.createGroup(CreateGroupRequestBody(userId, name)).handle()
    }

    override suspend fun getGroupTasks(groupId: String, requireNew: Boolean): Result<List<Task>> {
        if(groupId in cachedGroupTasks && !requireNew){
            return Result.Success(cachedGroupTasks[groupId]!!)
        }
        val result = service.getGroupTasks(groupId).handle()
        if(result is Result.Success){
            cachedGroupTasks[groupId] = result.result
        }
        return result
    }

    override suspend fun addMemberToGroup(
        groupId: String,
        memberId: String
    ): Result<GroupMember> {
        return service.addMemberToGroup(groupId, AddMemberToGroupRequestBody(memberId)).handle()
    }
}