package ru.dayone.main.my_groups.presentation.group.state_hosting

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.main.my_groups.R
import ru.dayone.main.my_groups.domain.repository.GroupsRepository
import ru.dayone.tasksplitter.common.exceptions.RequestCanceledException
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.UIText
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GroupStateMachine @Inject constructor(
    private val repository: GroupsRepository
) : BaseStateMachine<GroupEffect, GroupState, GroupAction>(
    initialState = GroupState()
) {
    init {
        spec {
            inState<GroupState> {
                on<GroupAction.GetTasks> { action, state ->
                    updateEffect(GroupEffect.StartLoading)
                    val result = repository.getGroupTasks(action.groupId, action.requireNew)
                    Log.d("GroupStateMachine", result.toString())
                    updateEffect(GroupEffect.StopLoading)
                    when (result) {
                        is Result.Success -> {
                            return@on state.mutate { state.snapshot.copy(tasks = result.result) }
                        }

                        is Result.Error -> {
                            val errorTextId: Int = when (result.exception) {
                                is RequestCanceledException -> {
                                    R.string.error_request_canceled
                                }

                                else -> {
                                    R.string.error_something_went_wrong
                                }
                            }
                            return@on state.mutate {
                                state.snapshot.copy(
                                    error = UIText.StringResource(
                                        errorTextId
                                    )
                                )
                            }
                        }
                    }
                }
                on<GroupAction.GetUsersFromGroupMembers> { action, state ->
                    updateEffect(GroupEffect.StartLoading)
                    val result = repository.getUsersFromGroupMembers(action.groupMembers)
                    Log.d("GroupStateMachine", result.toString())
                    updateEffect(GroupEffect.StopLoading)
                    when (result) {
                        is Result.Success -> {
                            return@on state.mutate { state.snapshot.copy(users = result.result) }
                        }

                        is Result.Error -> {
                            val errorTextId: Int = when (result.exception) {
                                is RequestCanceledException -> {
                                    R.string.error_request_canceled
                                }

                                else -> {
                                    R.string.error_something_went_wrong
                                }
                            }
                            return@on state.mutate {
                                state.snapshot.copy(
                                    error = UIText.StringResource(
                                        errorTextId
                                    )
                                )
                            }
                        }
                    }
                }
                on<GroupAction.AddUserToGroup> { action, state ->
                    updateEffect(GroupEffect.StartLoading)
                    val result = repository.addMemberToGroup(action.userId, action.groupId)
                    Log.d("GroupStateMachine", result.toString())
                    updateEffect(GroupEffect.StopLoading)
                    when (result) {
                        is Result.Success -> {
                            updateEffect(GroupEffect.UserAdded)
                            return@on state.noChange()
                        }

                        is Result.Error -> {
                            val errorTextId: Int = when (result.exception) {
                                is RequestCanceledException -> {
                                    R.string.error_request_canceled
                                }

                                else -> {
                                    R.string.error_something_went_wrong
                                }
                            }
                            return@on state.mutate {
                                state.snapshot.copy(
                                    error = UIText.StringResource(
                                        errorTextId
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