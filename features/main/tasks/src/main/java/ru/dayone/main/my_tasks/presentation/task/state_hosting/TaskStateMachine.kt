package ru.dayone.main.my_tasks.presentation.task.state_hosting

import ru.dayone.main.my_tasks.domain.repository.TasksRepository
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import javax.inject.Inject

class TaskStateMachine @Inject constructor(
    private val repository: TasksRepository
) : BaseStateMachine<TaskEffect, TaskState, TaskAction>(initialState = TaskState()) {
    init {
        spec {

        }
    }
}