package ru.dayone.auth.presentation.sign_in

import ru.dayone.auth.presentation.sign_in.state_hosting.AuthAction
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthEffect
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthState
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthStateMachine
import ru.dayone.tasksplitter.common.utils.StatefulViewModel
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    @EncryptedSharedPrefsQualifier private val stateMachine: AuthStateMachine
) : StatefulViewModel<AuthState, AuthEffect, AuthAction>(
    initialState = AuthState.Content(),
    stateMachine = stateMachine
)