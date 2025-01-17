package ru.dayone.auth.presentation.sign_in.state_hosting

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.auth.R
import ru.dayone.auth.data.exception.NoSuchAuthTypeException
import ru.dayone.auth.data.exception.RequestCanceledException
import ru.dayone.auth.data.usecase.validate_password.PasswordValidationResult
import ru.dayone.auth.data.usecase.validate_password.ValidatePasswordUseCase
import ru.dayone.auth.domain.repository.AuthRepository
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.UIText
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class AuthStateMachine @Inject constructor(
    private val authRepository: AuthRepository,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : BaseStateMachine<AuthEffect, AuthState, AuthIntent>(
    initialState = AuthState.Content()
) {
    init {
        spec {
            inState<AuthState.Content> {
                on<AuthIntent.OnPasswordChanged>{action, state ->
                    if(validatePasswordUseCase(action.password) is PasswordValidationResult.TooShort){
                        return@on state.mutate {
                            state.snapshot.copy(
                                error = UIText.StringResource(R.string.error_password_too_short)
                            )
                        }
                    }
                    return@on state.mutate {
                        state.snapshot.copy(
                            error = null
                        )
                    }
                }
                on<AuthIntent.SignInUser>{ action, state ->
                    if(state.snapshot.error != null){
                        return@on state.noChange()
                    }
                    updateEffect(AuthEffect.Loading())
                    val result = authRepository.signIn(action.authType)
                    when(result){
                        is Result.Success -> {
                            Log.d("AuthStateMachine", result.result.toString())
                            when(result.result.nickname){
                                null -> {
                                    updateEffect(AuthEffect.ToSignUp())
                                }
                                else -> {
                                    updateEffect(AuthEffect.ToMain())
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
                            val error = when(result.exception){
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

                                else -> {
                                    UIText.StringResource(
                                        R.string.error_something_went_wrong
                                    )
                                }
                            }
                            return@on state.mutate {
                                state.snapshot.copy(
                                    error = error
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}