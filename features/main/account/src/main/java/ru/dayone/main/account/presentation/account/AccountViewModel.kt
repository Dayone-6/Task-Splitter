package ru.dayone.main.account.presentation.account

import ru.dayone.main.account.presentation.account.state_hosting.AccountAction
import ru.dayone.main.account.presentation.account.state_hosting.AccountEffect
import ru.dayone.main.account.presentation.account.state_hosting.AccountState
import ru.dayone.main.account.presentation.account.state_hosting.AccountStateMachine
import ru.dayone.tasksplitter.common.utils.StatefulViewModel
import javax.inject.Inject


class AccountViewModel @Inject constructor(
    accountStateMachine: AccountStateMachine
) : StatefulViewModel<AccountState, AccountEffect, AccountAction>(
    stateMachine = accountStateMachine,
    initialState = AccountState()
)