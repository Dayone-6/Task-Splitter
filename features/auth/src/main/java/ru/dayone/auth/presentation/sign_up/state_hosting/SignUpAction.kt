package ru.dayone.auth.presentation.sign_up.state_hosting

import ru.dayone.auth.domain.model.User

sealed class SignUpAction {
    data class SignUp(val name: String, val nickname: String) : SignUpAction()

    data class NicknameChanged(val nickname: String): SignUpAction()
}