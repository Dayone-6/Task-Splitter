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
    val repository: AccountRepository
)
    : BaseStateMachine<AccountEffect, AccountState, AccountAction>(initialState = AccountState()) {
    init {
        spec {
            inState<AccountState> {
                on<AccountAction.SignOut> { action, state ->
                    updateEffect(AccountEffect.StartLoading())
                    val result = repository.signOut()
                    when(result){
                        is Result.Success -> {
                            updateEffect(AccountEffect.NavigateToSignIn())
                            return@on state.noChange()
                        }

                        is Result.Error -> {
                            updateEffect(AccountEffect.StopLoading())
                            return@on state.mutate {
                                state.snapshot.copy(error = UIText.StringResource(R.string.error_something_went_wrong))
                            }
                        }
                    }
                }
            }
        }
    }
}