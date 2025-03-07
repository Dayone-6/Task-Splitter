package ru.dayone.main.account.presentation.completed_tasks

import ru.dayone.main.account.presentation.completed_tasks.state_hosting.CompletedTasksAction
import ru.dayone.main.account.presentation.completed_tasks.state_hosting.CompletedTasksEffect
import ru.dayone.main.account.presentation.completed_tasks.state_hosting.CompletedTasksState
import ru.dayone.main.account.presentation.completed_tasks.state_hosting.CompletedTasksStateMachine
import ru.dayone.tasksplitter.common.utils.StatefulViewModel
import javax.inject.Inject

class CompletedTasksViewModel @Inject constructor(
    stateMachine: CompletedTasksStateMachine
) : StatefulViewModel<CompletedTasksState, CompletedTasksEffect, CompletedTasksAction>(
        initialState = CompletedTasksState(),
        stateMachine = stateMachine
    )