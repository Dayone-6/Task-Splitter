package ru.dayone.main.account.presentation.friends.state_hosting

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.main.account.domain.datasource.FriendsLocalDataSource
import ru.dayone.main.account.domain.datasource.FriendsRemoteDataSource
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class FriendsStateMachine @Inject constructor(
    private val localDataSource: FriendsLocalDataSource,
    private val remoteDataSource: FriendsRemoteDataSource
) : BaseStateMachine<FriendsEffect, FriendsState, FriendsAction>(
    initialState = FriendsState()
) {
    init {
        spec {
            inState<FriendsState> {

            }
        }
    }
}