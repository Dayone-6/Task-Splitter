package ru.dayone.main.my_groups.presentation.group.state_hosting

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.main.my_groups.domain.repository.GroupsRepository
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.UIText
import javax.inject.Inject

private const val TAG = "GroupStateMachine"

@OptIn(ExperimentalCoroutinesApi::class)
class GroupStateMachine @Inject constructor(
    private val repository: GroupsRepository
) : BaseStateMachine<GroupEffect, GroupState, GroupAction>(
    initialState = GroupState()
) {
    init {
        spec {
            inState<GroupState> {
                on<GroupAction.GetCurrentUser> { action, state ->
                    Log.d(TAG, "Start")
                    val result = repository.getCurrentUser()
                    Log.d(TAG, result.toString())
                    when (result) {
                        is Result.Success -> {
                            return@on state.override {
                                this.copy(currentUser = result.result)
                            }
                        }

                        is Result.Error -> {
                            return@on state.mutate {
                                state.snapshot.copy(
                                    error = UIText.Exception(
                                        result.exception
                                    )
                                )
                            }
                        }
                    }
                }
                on<GroupAction.GetTasks> { action, state ->
                    updateEffect(GroupEffect.StartLoading)
                    val result = repository.getGroupTasks(action.groupId, action.requireNew)
                    Log.d(TAG, result.toString())
                    updateEffect(GroupEffect.StopLoading)
                    when (result) {
                        is Result.Success -> {
                            if (action.requireNew) {
                                updateEffect(GroupEffect.RequiredTasksLoaded)
                            }
                            return@on state.override {
                                state.snapshot.copy(
                                    tasks = result.result,
                                    users = users
                                )
                            }
                        }

                        is Result.Error -> {
                            return@on state.mutate {
                                state.snapshot.copy(
                                    error = UIText.Exception(
                                        result.exception
                                    )
                                )
                            }
                        }
                    }
                }
                on<GroupAction.GetUsersFromGroupMembers> { action, state ->
                    updateEffect(GroupEffect.StartLoading)
                    val result = repository.getUsersFromGroupMembers(action.groupMembers)
                    Log.d(TAG, result.toString())
                    updateEffect(GroupEffect.StopLoading)
                    when (result) {
                        is Result.Success -> {
                            return@on state.override {
                                state.snapshot.copy(
                                    tasks = tasks,
                                    users = result.result
                                )
                            }
                        }

                        is Result.Error -> {
                            return@on state.mutate {
                                state.snapshot.copy(
                                    error = UIText.Exception(
                                        result.exception
                                    )
                                )
                            }
                        }
                    }
                }
                on<GroupAction.AddUserToGroup> { action, state ->
                    updateEffect(GroupEffect.StartLoading)
                    val result = repository.addMemberToGroup(action.groupId, action.userId)
                    Log.d(TAG, result.toString())
                    updateEffect(GroupEffect.StopLoading)
                    when (result) {
                        is Result.Success -> {
                            updateEffect(GroupEffect.UserAdded)
                            return@on state.noChange()
                        }

                        is Result.Error -> {
                            return@on state.mutate {
                                state.snapshot.copy(
                                    error = UIText.Exception(
                                        result.exception
                                    )
                                )
                            }
                        }
                    }
                }

                on<GroupAction.GetUserFriends> { action, state ->
                    updateEffect(GroupEffect.StartLoading)
                    val result = repository.getUserFriends()
                    updateEffect(GroupEffect.StopLoading)
                    Log.d(TAG, result.toString())
                    when (result) {
                        is Result.Success -> {
                            return@on state.override { state.snapshot.copy(friends = result.result) }
                        }

                        is Result.Error -> {
                            return@on state.mutate {
                                state.snapshot.copy(
                                    error = UIText.Exception(
                                        result.exception
                                    )
                                )
                            }
                        }
                    }
                }

                on<GroupAction.CreateTask> { action, state ->
                    updateEffect(GroupEffect.StartLoading)
                    val result =
                        repository.createTask(action.groupId, action.title, action.description)
                    updateEffect(GroupEffect.StopLoading)
                    Log.d(TAG, result.toString())
                    when (result) {
                        is Result.Success -> {
                            updateEffect(GroupEffect.TaskCreated)
                            return@on state.noChange()
                        }

                        is Result.Error -> {
                            return@on state.mutate {
                                state.snapshot.copy(
                                    error = UIText.Exception(
                                        result.exception
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