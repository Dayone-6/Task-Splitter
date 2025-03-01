package ru.dayone.main.account.presentation

import ru.dayone.main.account.presentation.state_hosting.AccountAction
import ru.dayone.main.account.presentation.state_hosting.AccountEffect
import ru.dayone.main.account.presentation.state_hosting.AccountState
import ru.dayone.main.account.presentation.state_hosting.AccountStateMachine
import ru.dayone.tasksplitter.common.utils.StatefulViewModel
import javax.inject.Inject


class AccountViewModel @Inject constructor(
    accountStateMachine: AccountStateMachine
) :
    StatefulViewModel<AccountState, AccountEffect, AccountAction>(
        stateMachine = accountStateMachine,
        initialState = AccountState()
    )