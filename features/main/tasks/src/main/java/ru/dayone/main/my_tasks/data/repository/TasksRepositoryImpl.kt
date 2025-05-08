package ru.dayone.main.my_tasks.data.repository

import ru.dayone.main.my_tasks.domain.datasource.TasksLocalDataSource
import ru.dayone.main.my_tasks.domain.datasource.TasksRemoteDataSource
import ru.dayone.main.my_tasks.domain.repository.TasksRepository
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val localDataSource: TasksLocalDataSource,
    private val remoteDataSource: TasksRemoteDataSource
): TasksRepository {
}