package ru.dayone.main.my_tasks.presentation.task.state_hosting

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.main.my_tasks.domain.repository.TasksRepository
import ru.dayone.tasksplitter.common.theme.currentScheme
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.UIText
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class TaskStateMachine @Inject constructor(
    private val repository: TasksRepository
) : BaseStateMachine<TaskEffect, TaskState, TaskAction>(initialState = TaskState()) {

    private val TAG = "TaskStateMachine"

    init {
        spec {
            inState<TaskState> {
                on<TaskAction.PayForTask> { action, state ->
                    updateEffect(TaskEffect.StartLoading)
                    val result = repository.payForTask(action.taskId)
                    Log.d(TAG, result.toString())
                    updateEffect(TaskEffect.StopLoading)
                    when (result) {
                        is Result.Success -> {
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

                on<TaskAction.LoadUser> { action, state ->
                    val result = repository.getUser(action.userId)
                    when (result) {
                        is Result.Success -> {
                            return@on state.override {
                                this.copy(executor = result.result)
                            }
                        }

                        is Result.Error -> {
                            return@on state.mutate {
                                state.snapshot.copy(error = UIText.Exception(result.exception))
                            }
                        }
                    }
                }

                on<TaskAction.LoadCurrentUser> { action, state ->
                    val result = repository.getCurrentUser()
                    when (result) {
                        is Result.Success -> {
                            return@on state.override {
                                this.copy(user = result.result)
                            }
                        }

                        is Result.Error -> {
                            return@on state.override {
                                state.snapshot.copy(
                                    error = UIText.Exception(
                                        result.exception
                                    )
                                )
                            }
                        }
                    }
                }

                on<TaskAction.Vote> { action, state ->
                    updateEffect(TaskEffect.StartLoading)
                    val result = repository.voteForTask(action.taskId, action.vote)
                    Log.d(TAG, result.toString())
                    updateEffect(TaskEffect.StopLoading)
                    updateEffect(TaskEffect.VotesLoaded)
                    when (result) {
                        is Result.Success -> {
                            updateEffect(TaskEffect.VoteSucceed)
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

                on<TaskAction.EndTask> { action, state ->
                    updateEffect(TaskEffect.StartLoading)
                    val result = repository.endTask(action.taskId)
                    Log.d(TAG, result.toString())
                    updateEffect(TaskEffect.StopLoading)
                    when (result) {
                        is Result.Success -> {
                            updateEffect(TaskEffect.EndTaskSucceed)
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

                on<TaskAction.LoadVotes> { action, state ->
                    updateEffect(TaskEffect.StartLoading)
                    val result = repository.getVotes(action.taskId)
                    Log.d(TAG, result.toString())
                    updateEffect(TaskEffect.StopLoading)
                    updateEffect(TaskEffect.VotesLoaded)
                    when (result) {
                        is Result.Success -> {
                            return@on state.override {
                                this.copy(votes = result.result)
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
            }
        }
    }
}