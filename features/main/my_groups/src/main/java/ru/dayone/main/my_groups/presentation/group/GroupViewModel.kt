package ru.dayone.main.my_groups.presentation.group

import ru.dayone.main.my_groups.presentation.group.state_hosting.GroupAction
import ru.dayone.main.my_groups.presentation.group.state_hosting.GroupEffect
import ru.dayone.main.my_groups.presentation.group.state_hosting.GroupState
import ru.dayone.main.my_groups.presentation.group.state_hosting.GroupStateMachine
import ru.dayone.tasksplitter.common.utils.StatefulViewModel
import javax.inject.Inject

class GroupViewModel @Inject constructor(
    stateMachine: GroupStateMachine
) : StatefulViewModel<GroupState, GroupEffect, GroupAction>(
    initialState = GroupState(), stateMachine = stateMachine
)