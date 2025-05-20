package ru.dayone.main.my_tasks.presentation.my_tasks.state_hosting

import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.tasksplitter.common.utils.UIText

data class MyTasksScreenState (
    var error: UIText? = null,
    var tasks: List<Task>? = null
)