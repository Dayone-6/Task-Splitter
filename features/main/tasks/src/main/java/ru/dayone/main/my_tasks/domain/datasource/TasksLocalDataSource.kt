package ru.dayone.main.my_tasks.domain.datasource

import ru.dayone.tasksplitter.common.models.User

interface TasksLocalDataSource {
    suspend fun getCurrentUser() : User?
    suspend fun updateUserPoints(points: Int)
}