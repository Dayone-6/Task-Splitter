package ru.dayone.main.my_groups.presentation.group.state_hosting

import ru.dayone.main.my_groups.domain.models.UserWithScore
import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.UIText

data class GroupState(
    var tasks: List<Task>? = null,
    var users: List<UserWithScore>? = null,
    var friends: List<User>? = null,
    var currentUser: User? = null,
    var error: UIText? = null
)