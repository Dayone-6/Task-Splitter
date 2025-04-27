package ru.dayone.main.account.presentation.friends.state_hosting

import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.UIText

data class FriendsState(
    var user: User? = null,
    var friends: List<User>? = null,
    var foundUsers: List<User>? = null,
    var error: UIText? = null
)