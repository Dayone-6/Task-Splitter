package ru.dayone.main.my_groups.domain.repository

import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.main.my_groups.data.network.models.GroupMember
import ru.dayone.main.my_groups.domain.models.UserWithScore
import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.Result

interface GroupsRepository {
    suspend fun getMyGroups(requireNew: Boolean): Result<List<Group>>

    suspend fun createGroup(name: String): Result<Group>

    suspend fun getGroupTasks(groupId: String, requireNew: Boolean): Result<List<Task>>

    suspend fun addMemberToGroup(groupId: String, memberId: String): Result<GroupMember>

    suspend fun getGroupMembers(groupId: String): Result<List<UserWithScore>>

    suspend fun getUserFriends(): Result<List<User>>

    suspend fun getCurrentUser() : Result<User>

    suspend fun createTask(groupId: String, title: String, description: String): Result<Task>
}