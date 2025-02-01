package ru.dayone.auth.presentation.sign_up.state_hosting

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.auth.R
import ru.dayone.auth.data.exception.NicknameIsAlreadyInUseException
import ru.dayone.auth.data.usecase.validate_nickname.NicknameValidationResult
import ru.dayone.auth.data.usecase.validate_nickname.ValidateNicknameUseCase
import ru.dayone.auth.domain.repository.AuthRepository
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.UIText
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class SignUpStateMachine @Inject constructor(
    private val authRepository: AuthRepository,
    private val validateNicknameUseCase: ValidateNicknameUseCase
) : BaseStateMachine<SignUpEffect, SignUpState, SignUpAction>(initialState = SignUpState()) {
    init {
        spec {
            inState<SignUpState> {
                on<SignUpAction.SignUp> { action, state ->
                    updateEffect(SignUpEffect.StartLoading())
                    val result = authRepository.signUp(action.name, action.nickname)
                    return@on when (result) {
                        is Result.Success -> {
                            updateEffect(SignUpEffect.ToMain())
                            state.noChange()
                        }

                        is Result.Error -> {
                            val errorMessage = when (result.exception) {
                                is NicknameIsAlreadyInUseException -> {
                                    R.string.error_nickname_is_already_in_use
                                }

                                else -> {
                                    R.string.error_something_went_wrong
                                }
                            }
                            updateEffect(SignUpEffect.StopLoading())
                            state.override {
                                state.snapshot.copy(error = UIText.StringResource(errorMessage))
                            }
                        }
                    }
                }

                on<SignUpAction.NicknameChanged> { action, state ->
                    return@on when (validateNicknameUseCase(action.nickname)) {
                        is NicknameValidationResult.Succeed -> state.mutate {
                            state.snapshot.copy(
                                nicknameError = null
                            )
                        }

                        is NicknameValidationResult.TooShort -> state.mutate {
                            state.snapshot.copy(
                                nicknameError = UIText.StringResource(R.string.error_nickname_too_short)
                            )
                        }
                    }
                }
            }
        }
    }
}