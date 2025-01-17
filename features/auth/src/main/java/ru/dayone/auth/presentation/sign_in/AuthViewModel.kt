package ru.dayone.auth.presentation.sign_in

import androidx.lifecycle.ViewModel
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthStateMachine
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    @EncryptedSharedPrefsQualifier private val stateMachine: AuthStateMachine
) : ViewModel() {

}