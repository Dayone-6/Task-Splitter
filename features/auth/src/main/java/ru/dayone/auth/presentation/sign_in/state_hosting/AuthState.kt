package ru.dayone.auth.presentation.sign_in.state_hosting

import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.UIText

sealed class AuthState {
    data class Content(
        var user: User? = null,
        var error: UIText? = null,
        var passwordError: UIText? = null,
        var isVerificationCodeError: Boolean = false
    ) : AuthState()
}