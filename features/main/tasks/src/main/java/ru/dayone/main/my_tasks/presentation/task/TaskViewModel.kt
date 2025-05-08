package ru.dayone.main.my_tasks.presentation.task

import ru.dayone.main.my_tasks.presentation.task.state_hosting.TaskAction
import ru.dayone.main.my_tasks.presentation.task.state_hosting.TaskEffect
import ru.dayone.main.my_tasks.presentation.task.state_hosting.TaskState
import ru.dayone.main.my_tasks.presentation.task.state_hosting.TaskStateMachine
import ru.dayone.tasksplitter.common.utils.StatefulViewModel
import javax.inject.Inject

class TaskViewModel @Inject constructor(
    stateMachine: TaskStateMachine
) : StatefulViewModel<TaskState, TaskEffect, TaskAction>(
    initialState = TaskState(),
    stateMachine = stateMachine
) {
}