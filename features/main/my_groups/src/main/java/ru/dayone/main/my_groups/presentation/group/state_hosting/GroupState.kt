package ru.dayone.main.my_groups.presentation.group.state_hosting

import ru.dayone.main.my_groups.data.network.models.Task
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.UIText

data class GroupState(
    var tasks: List<Task>? = null,
    var users: List<User>? = null,
    var error: UIText? = null
)