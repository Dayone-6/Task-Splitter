package ru.dayone.auth.presentation.sign_up

import ru.dayone.auth.presentation.sign_up.state_hosting.SignUpAction
import ru.dayone.auth.presentation.sign_up.state_hosting.SignUpEffect
import ru.dayone.auth.presentation.sign_up.state_hosting.SignUpState
import ru.dayone.auth.presentation.sign_up.state_hosting.SignUpStateMachine
import ru.dayone.tasksplitter.common.utils.StatefulViewModel
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    signUpStateMachine: SignUpStateMachine
) : StatefulViewModel<SignUpState, SignUpEffect, SignUpAction>(
    initialState = SignUpState(),
    stateMachine = signUpStateMachine
)