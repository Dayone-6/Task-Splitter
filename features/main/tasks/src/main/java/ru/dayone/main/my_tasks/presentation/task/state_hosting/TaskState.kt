package ru.dayone.main.my_tasks.presentation.task.state_hosting

import ru.dayone.main.my_tasks.data.network.models.Vote
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.UIText

data class TaskState(
    var error: UIText? = null,
    var votes: List<Vote>? = null,
    var executor: User? = null,
    var user: User? = null
)