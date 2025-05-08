package ru.dayone.main.my_tasks.data.datasource

import ru.dayone.main.my_tasks.data.network.TasksService
import ru.dayone.main.my_tasks.domain.datasource.TasksRemoteDataSource
import javax.inject.Inject

class TasksRemoteDataSourceImpl @Inject constructor(
    private val service: TasksService
) : TasksRemoteDataSource {
}