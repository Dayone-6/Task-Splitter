package ru.dayone.auth.presentation.sign_in.state_hosting

import ru.dayone.auth.domain.model.User
import ru.dayone.tasksplitter.common.utils.UIText

sealed class AuthState {
    data class Content(
        var user: User? = null,
        var error: UIText? = null
    ) : AuthState()
}