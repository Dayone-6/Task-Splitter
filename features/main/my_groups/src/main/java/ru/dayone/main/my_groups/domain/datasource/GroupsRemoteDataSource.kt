package ru.dayone.main.my_groups.domain.datasource

import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.main.my_groups.data.network.models.GroupMember
import ru.dayone.main.my_groups.data.network.models.Task
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result

interface GroupsRemoteDataSource {
    suspend fun getGroups(userId: String, requireNew: Boolean): Result<List<Group>>

    suspend fun createGroup(userId: String, name: String): Result<Group>

    suspend fun getGroupTasks(groupId: String, requireNew: Boolean): Result<List<Task>>

    suspend fun addMemberToGroup(groupId: String, memberId: String): Result<GroupMember>

    suspend fun getUserFromGroupMember(groupMember: GroupMember): Result<User>

    suspend fun getUserFriends(userId: String): Result<List<User>>

    suspend fun createTask(groupId: String, title: String, description: String): Result<Task>
}