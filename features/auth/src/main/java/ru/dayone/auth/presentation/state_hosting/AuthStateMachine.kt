package ru.dayone.auth.presentation.state_hosting

import ru.dayone.auth.domain.repository.AuthRepository
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import javax.inject.Inject

class AuthStateMachine @Inject constructor(
    private val authRepository: AuthRepository
)
    : BaseStateMachine<AuthEffect, AuthState, AuthIntent>(
    initialState = AuthState.Content()
) {
}