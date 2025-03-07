package ru.dayone.main.account.presentation.friends

import ru.dayone.main.account.presentation.friends.state_hosting.FriendsAction
import ru.dayone.main.account.presentation.friends.state_hosting.FriendsEffect
import ru.dayone.main.account.presentation.friends.state_hosting.FriendsState
import ru.dayone.main.account.presentation.friends.state_hosting.FriendsStateMachine
import ru.dayone.tasksplitter.common.utils.StatefulViewModel
import javax.inject.Inject

class FriendsViewModel @Inject constructor(
    stateMachine: FriendsStateMachine
) : StatefulViewModel<FriendsState, FriendsEffect, FriendsAction>(
    initialState = FriendsState(), stateMachine = stateMachine
)