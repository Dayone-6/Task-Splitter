package ru.dayone.main.my_tasks.presentation.my_tasks

import ru.dayone.main.my_tasks.presentation.my_tasks.state_hosting.MyTasksScreenAction
import ru.dayone.main.my_tasks.presentation.my_tasks.state_hosting.MyTasksScreenEffect
import ru.dayone.main.my_tasks.presentation.my_tasks.state_hosting.MyTasksScreenState
import ru.dayone.main.my_tasks.presentation.my_tasks.state_hosting.MyTasksScreenStateMachine
import ru.dayone.tasksplitter.common.utils.StatefulViewModel
import javax.inject.Inject

class MyTasksScreenViewModel @Inject constructor(
    stateMachine: MyTasksScreenStateMachine
) : StatefulViewModel<MyTasksScreenState, MyTasksScreenEffect, MyTasksScreenAction>(
    initialState = MyTasksScreenState(),
    stateMachine = stateMachine
)