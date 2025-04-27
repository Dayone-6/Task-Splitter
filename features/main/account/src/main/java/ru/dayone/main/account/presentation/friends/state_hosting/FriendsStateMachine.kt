package ru.dayone.main.account.presentation.friends.state_hosting

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.main.account.R

import ru.dayone.main.account.domain.repository.AccountRepository
import ru.dayone.tasksplitter.common.exceptions.RequestCanceledException
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
    private val TAG = "FriendsStateMachine"

    init {
        spec {
            inState<FriendsState> {
                on<FriendsAction.GetUser> { action, state ->
                    val result = repository.getUser()
                    Log.d(TAG, result.toString())
                    if (result == null) {
                        return@on state.override {
                            state.snapshot.copy(
                                error = UIText.StringResource(
                                    R.string.error_something_went_wrong
                                )
                            )
                        }
                    }
                    return@on state.override { state.snapshot.copy(user = result) }
                }
                on<FriendsAction.AddFriend> { action, state ->
                    updateEffect(FriendsEffect.StartLoading())
                    val result = repository.addFriend(action.friendId)
                    updateEffect(FriendsEffect.StopLoading())
                    Log.d(TAG, result.toString())
                    when (result) {
                        is Result.Success -> {
                            updateEffect(FriendsEffect.FriendAdded())
                            return@on state.noChange()
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

                on<FriendsAction.GetFriends> { action, state ->
                    updateEffect(FriendsEffect.StartLoading())
                    val result = repository.getFriends(action.userId)
                    updateEffect(FriendsEffect.StopLoading())
                    Log.d(TAG, result.toString())
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

                on<FriendsAction.SearchUsers>{action, state ->
                    updateEffect(FriendsEffect.StartLoading())
                    val result = repository.getUsersByNickname(action.userNickname)
                    Log.d(TAG, result.toString())
                    updateEffect(FriendsEffect.StopLoading())
                    when(result){
                        is Result.Success -> {
                            return@on state.override {
                                state.snapshot.copy(
                                    foundUsers = result.result
                                )
                            }
                        }
                        is Result.Error -> {
                            val errorTextId: Int = when(result.exception){
                                is RequestCanceledException -> {
                                    R.string.error_request_canceled
                                }
                                else -> {
                                    R.string.error_something_went_wrong
                                }
                            }
                            return@on state.override {
                                state.snapshot.copy(
                                    error = UIText.StringResource(errorTextId)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}