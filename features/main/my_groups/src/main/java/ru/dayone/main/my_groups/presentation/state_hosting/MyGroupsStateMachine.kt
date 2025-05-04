package ru.dayone.main.my_groups.presentation.state_hosting

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.main.my_groups.R
import ru.dayone.main.my_groups.domain.repository.MyGroupsRepository
import ru.dayone.tasksplitter.common.exceptions.RequestCanceledException
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import ru.dayone.tasksplitter.common.utils.Result
import ru.dayone.tasksplitter.common.utils.UIText
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class MyGroupsStateMachine @Inject constructor(
    private val repository: MyGroupsRepository
) :
    BaseStateMachine<MyGroupsEffect, MyGroupsState, MyGroupsAction>(
        initialState = MyGroupsState()
    ) {
    init {
        spec {
            inState<MyGroupsState> {
                on<MyGroupsAction.GetGroups>{action, state ->
                    updateEffect(MyGroupsEffect.StartLoading)
                    val result = repository.getMyGroups(action.requireNew)
                    updateEffect(MyGroupsEffect.StopLoading)
                    Log.d("MyGroupsStateMachine", result.toString())
                    when(result){
                        is Result.Success -> {
                            if(action.requireNew){
                                updateEffect(MyGroupsEffect.RequestedGroupsLoaded)
                            }
                            return@on state.mutate{ state.snapshot.copy(groups = result.result )}
                        }
                        is Result.Error -> {
                            val errorText: UIText = when(result.exception){
                                is RequestCanceledException -> {
                                    UIText.StringResource(R.string.error_request_canceled)
                                }

                                else -> {
                                    UIText.StringResource(R.string.error_something_went_wrong)
                                }
                            }
                            return@on state.mutate { state.snapshot.copy(error = errorText) }
                        }
                    }
                }

                on<MyGroupsAction.CreateGroup>{action, state ->
                    updateEffect(MyGroupsEffect.StartLoading)
                    val result = repository.createGroup(action.name)
                    Log.d("MyGroupsStateMachine", result.toString())
                    updateEffect(MyGroupsEffect.StopLoading)
                    when(result){
                        is Result.Success -> {
                            updateEffect(MyGroupsEffect.GroupCreated)
                            return@on state.noChange()
                        }
                        is Result.Error -> {
                            val errorText = when(result.exception){
                                is RequestCanceledException -> {
                                    UIText.StringResource(R.string.error_request_canceled)
                                }
                                else -> {
                                    UIText.StringResource(R.string.error_something_went_wrong)
                                }
                            }
                            return@on state.mutate { state.snapshot.copy(error = errorText) }
                        }
                    }
                }
            }
        }
    }
}
