package ru.dayone.main.my_groups.domain.repository

import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.tasksplitter.common.utils.Result

interface MyGroupsRepository {
    suspend fun getMyGroups(): Result<List<Group>>

    suspend fun createGroup(name: String): Result<Group>
}