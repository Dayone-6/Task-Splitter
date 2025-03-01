package ru.dayone.main.account.presentation.state_hosting

import ru.dayone.main.account.domain.repository.AccountRepository
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import javax.inject.Inject

class AccountStateMachine @Inject constructor(
    val repository: AccountRepository
)
    : BaseStateMachine<AccountEffect, AccountState, AccountAction>(initialState = AccountState()) {
    init {
        spec {

        }
    }
}