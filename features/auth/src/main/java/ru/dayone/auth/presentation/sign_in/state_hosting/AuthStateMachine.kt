package ru.dayone.auth.presentation.sign_in.state_hosting

import android.util.Log
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.auth.R
import ru.dayone.auth.data.AuthType
import ru.dayone.auth.data.exception.NoSuchAuthTypeException
import ru.dayone.auth.data.exception.RequestCanceledException
import ru.dayone.auth.data.usecase.validate_password.PasswordValidationResult
import ru.dayone.auth.data.usecase.validate_password.ValidatePasswordUseCase
import ru.dayone.auth.domain.repository.AuthRepository
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.UIText
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class AuthStateMachine @Inject constructor(
    private val authRepository: AuthRepository,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : BaseStateMachine<AuthEffect, AuthState, AuthAction>(
    initialState = AuthState.Content()
) {
    init {
        spec {
            inState<AuthState.Content> {
                on<AuthAction.OnPasswordChanged> { action, state ->
                    if (validatePasswordUseCase(action.password) is PasswordValidationResult.TooShort) {
                        return@on state.mutate {
                            state.snapshot.copy(
                                passwordError = UIText.StringResource(R.string.error_password_too_short)
                            )
                        }
                    }
                    return@on state.mutate {
                        state.snapshot.copy(
                            passwordError = null
                        )
                    }
                }

                on<AuthAction.SignInUser> { action, state ->
                    if (state.snapshot.passwordError != null) {
                        return@on state.noChange()
                    }
                    updateEffect(AuthEffect.StartLoading)
                    when (val result = authRepository.signIn(action.authType)) {
                        is Result.Success -> {
                            Log.d("AuthStateMachine", result.result.toString())
                            when (result.result.nickname) {
                                null -> {
                                    updateEffect(AuthEffect.ToSignUp)
                                }

                                else -> {
                                    updateEffect(AuthEffect.ToMain)
                                }
                            }
                            return@on state.override {
                                AuthState.Content(
                                    user = user,
                                    error = null
                                )
                            }
                        }

                        is Result.Error -> {
                            Log.e("AuthStateMachine", "Error", result.exception)
                            updateEffect(AuthEffect.StopLoading)
                            var isVerificationCodeError = state.snapshot.isVerificationCodeError
                            val error = when (result.exception) {
                                is NoSuchAuthTypeException -> {
                                    UIText.StringResource(
                                        R.string.error_this_type_isnt_working
                                    )
                                }

                                is RequestCanceledException -> {
                                    UIText.StringResource(
                                        R.string.error_request_canceled
                                    )
                                }

                                is FirebaseAuthInvalidCredentialsException -> {
                                    UIText.StringResource(
                                        if (action.authType is AuthType.Phone) {
                                            isVerificationCodeError = true
                                            R.string.error_invalid_verification_code
                                        } else {
                                            R.string.error_invalid_credentials
                                        }
                                    )
                                }

                                is FirebaseTooManyRequestsException -> {
                                    UIText.StringResource(
                                        R.string.error_too_many_requests
                                    )
                                }

                                else -> {
                                    UIText.StringResource(
                                        R.string.error_something_went_wrong
                                    )
                                }
                            }
                            return@on state.mutate {
                                state.snapshot.copy(
                                    error = error,
                                    isVerificationCodeError = isVerificationCodeError
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}