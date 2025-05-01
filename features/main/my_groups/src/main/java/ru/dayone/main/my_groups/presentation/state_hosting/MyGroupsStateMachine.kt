package ru.dayone.main.my_groups.presentation.state_hosting

import ru.dayone.main.my_groups.domain.repository.MyGroupsRepository
import ru.dayone.tasksplitter.common.utils.BaseStateMachine

class MyGroupsStateMachine(
    private val repository: MyGroupsRepository
) :
    BaseStateMachine<MyGroupsEffect, MyGroupsState, MyGroupsAction>(
        initialState = MyGroupsState()
    ) {
    init {
        spec {

        }
    }
}
