package ru.dayone.main.account.presentation.friends.state_hosting

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.main.account.R

import ru.dayone.main.account.domain.repository.AccountRepository
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.UIText
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class FriendsStateMachine @Inject constructor(
    private val repository: AccountRepository
) : BaseStateMachine<FriendsEffect, FriendsState, FriendsAction>(
    initialState = FriendsState()
) {
    init {
        spec {
            inState<FriendsState> {
                on<FriendsAction.AddFriend> { action, state ->
                    updateEffect(FriendsEffect.StartLoading())
                    val result = repository.addFriend(action.friendId)
                    Log.d("FriendsStateMachine", result.toString())
                    when (result) {
                        is Result.Success -> {
                            updateEffect(FriendsEffect.FriendAdded())
                            return@on state.noChange()
                        }

                        is Result.Error -> {
                            updateEffect(FriendsEffect.StopLoading())
                            return@on state.override {
                                state.snapshot.copy(
                                    error = UIText.StringResource(
                                        R.string.error_something_went_wrong
                                    )
                                )
                            }
                        }
                    }
                }

                on<FriendsAction.GetFriends> { action, state ->
                    updateEffect(FriendsEffect.StartLoading())
                    val result = repository.getFriends(action.userId)
                    updateEffect(FriendsEffect.StopLoading())
                    Log.d("FriendsStateMachine", result.toString())
                    when (result) {
                        is Result.Success -> {
                            return@on state.override { state.snapshot.copy(friends = result.result) }
                        }

                        is Result.Error -> {
                            return@on state.override {
                                state.snapshot.copy(
                                    error = UIText.StringResource(
                                        R.string.error_something_went_wrong
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}