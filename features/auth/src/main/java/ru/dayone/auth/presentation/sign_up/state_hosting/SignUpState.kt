package ru.dayone.auth.presentation.sign_up.state_hosting

import ru.dayone.tasksplitter.common.utils.UIText

data class SignUpState (
    val nicknameError: UIText? = null,
    val error: UIText? = null
)