package ru.dayone.auth.presentation

import androidx.lifecycle.ViewModel
import ru.dayone.auth.presentation.state_hosting.AuthStateMachine
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val stateMachine: AuthStateMachine
) : ViewModel() {

}