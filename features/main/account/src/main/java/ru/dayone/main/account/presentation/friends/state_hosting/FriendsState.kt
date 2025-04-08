package ru.dayone.main.account.presentation.friends.state_hosting

import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.UIText

data class FriendsState(
    var friends: List<User>? = null,
    var error: UIText? = null
)