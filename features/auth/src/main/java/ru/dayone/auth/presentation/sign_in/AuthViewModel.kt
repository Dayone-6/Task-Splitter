package ru.dayone.auth.presentation.sign_in

import android.app.Activity
import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.launch
import ru.dayone.auth.domain.AuthType
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