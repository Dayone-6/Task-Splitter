package ru.dayone.main.account.presentation.completed_tasks.state_hosting

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.main.account.domain.datasource.UserDataLocalDataSource
import ru.dayone.main.account.domain.datasource.UserDataRemoteDataSource
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class CompletedTasksStateMachine @Inject constructor(
    private val localDataSource: UserDataLocalDataSource,
    private val remoteDataSource: UserDataRemoteDataSource
):
    BaseStateMachine<CompletedTasksEffect, CompletedTasksState, CompletedTasksAction>(
        initialState = CompletedTasksState()
    ) {
        init {
            spec {

            }
        }
}