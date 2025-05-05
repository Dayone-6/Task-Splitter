package ru.dayone.main.my_groups.presentation.group.state_hosting

import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.dayone.main.my_groups.domain.repository.GroupsRepository
import ru.dayone.tasksplitter.common.utils.BaseStateMachine
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class GroupStateMachine @Inject constructor(
    private val repository: GroupsRepository
) : BaseStateMachine<GroupEffect, GroupState, GroupAction>(
    initialState = GroupState()
){
    init {
        spec {

        }
    }
}