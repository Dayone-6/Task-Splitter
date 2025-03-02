package ru.dayone.main.account.presentation.state_hosting

import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.UIText

data class AccountState(
    val error: UIText? = null,
    val points: Int? = null,
    val user: User? = null
)