package ru.dayone.main.my_groups.domain.datasource

import ru.dayone.tasksplitter.common.models.User

interface GroupsLocalDataSource {
    suspend fun getUser(): User?
}