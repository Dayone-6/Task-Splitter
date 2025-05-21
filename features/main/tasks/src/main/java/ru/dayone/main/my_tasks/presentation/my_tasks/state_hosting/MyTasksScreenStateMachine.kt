package ru.dayone.main.my_tasks.presentation.my_tasks.state_hosting

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.main.my_tasks.domain.repository.TasksRepository
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.UIText
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class MyTasksScreenStateMachine @Inject constructor(
    private val repository: TasksRepository
) : BaseStateMachine<MyTasksScreenEffect, MyTasksScreenState, MyTasksScreenAction>(
    initialState = MyTasksScreenState()
) {
    init {
        spec {
            inState<MyTasksScreenState> {
                on<MyTasksScreenAction.GetGroupById>{action, state ->
                    updateEffect(MyTasksScreenEffect.StartLoading)
                    val result = repository.getGroupById(action.groupId)
                    updateEffect(MyTasksScreenEffect.StopLoading)
                    when(result){
                        is Result.Success -> {
                            updateEffect(MyTasksScreenEffect.NavigateToTaskScreen(result.result))
                            return@on state.noChange()
                        }
                        is Result.Error -> {
                            return@on state.mutate { state.snapshot.copy(error = UIText.Exception(result.exception)) }
                        }
                    }
                }
                on<MyTasksScreenAction.LoadTasks> { action, state ->
                    updateEffect(MyTasksScreenEffect.StartLoading)
                    val result = repository.getUserTasks(action.isCompleted)
                    updateEffect(MyTasksScreenEffect.StopLoading)
                    updateEffect(MyTasksScreenEffect.TasksLoaded)
                    when (result) {
                        is Result.Success -> {
                            return@on state.override {
                                this.copy(
                                    error = null,
                                    tasks = result.result
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
            }
        }
    }
}