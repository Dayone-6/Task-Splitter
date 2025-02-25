package ru.dayone.auth.presentation.sign_up.state_hosting

import ru.dayone.auth.domain.model.RegistrationUser

sealed class SignUpAction {
    data class SignUp(val registrationUser: RegistrationUser) : SignUpAction()

    data class NicknameChanged(val nickname: String): SignUpAction()
}