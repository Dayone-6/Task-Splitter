package ru.dayone.main.my_groups.domain.datasource

import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.tasksplitter.common.utils.Result

interface MyGroupsRemoteDataSource {
    suspend fun getGroups(userId: String, requireNew: Boolean): Result<List<Group>>

    suspend fun createGroup(userId: String, name: String): Result<Group>
}