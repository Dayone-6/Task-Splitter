package ru.dayone.main.account.presentation.state_hosting

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.main.account.R
import ru.dayone.main.account.domain.repository.AccountRepository
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.UIText
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class AccountStateMachine @Inject constructor(
    private val repository: AccountRepository
) : BaseStateMachine<AccountEffect, AccountState, AccountAction>(initialState = AccountState()) {
    init {
        spec {
            inState<AccountState> {
                on<AccountAction.SignOut> { _, state ->
                    updateEffect(AccountEffect.StartLoading())
                    val result = repository.signOut()
                    updateEffect(AccountEffect.StopLoading())
                    when (result) {
                        is Result.Success -> {
                            updateEffect(AccountEffect.NavigateToSignIn())
                            return@on state.noChange()
                        }

                        is Result.Error -> {
                            return@on state.override {
                                state.snapshot.copy(error = UIText.StringResource(R.string.error_something_went_wrong))
                            }
                        }
                    }
                }

                on<AccountAction.RequestPoints> { action, state ->
                    updateEffect(AccountEffect.StartLoading())
                    val result = repository.getPoints(action.userId)
                    updateEffect(AccountEffect.StopLoading())
                    when (result) {
                        is Result.Success -> {
                            return@on state.override {
                                state.snapshot.copy(points = result.result)
                            }
                        }

                        is Result.Error -> {
                            return@on state.override {
                                state.snapshot.copy(error = UIText.StringResource(R.string.error_something_went_wrong))
                            }
                        }
                    }
                }

                on<AccountAction.GetUser> { _, state ->
                    val result = repository.getUser()
                    return@on state.mutate { state.snapshot.copy(user = result) }
                }
            }
        }
    }
}