package ru.dayone.main.my_groups.presentation

import ru.dayone.main.my_groups.presentation.state_hosting.MyGroupsAction
import ru.dayone.main.my_groups.presentation.state_hosting.MyGroupsEffect
import ru.dayone.main.my_groups.presentation.state_hosting.MyGroupsState
import ru.dayone.main.my_groups.presentation.state_hosting.MyGroupsStateMachine
import ru.dayone.tasksplitter.common.utils.StatefulViewModel
import javax.inject.Inject

class MyGroupsViewModel @Inject constructor(
    stateMachine: MyGroupsStateMachine
) : StatefulViewModel<MyGroupsState, MyGroupsEffect, MyGroupsAction>(
    initialState = MyGroupsState(),
    stateMachine = stateMachine
)