package ru.dayone.main.my_tasks.presentation.my_tasks.state_hosting

import ru.dayone.main.my_tasks.data.network.models.Group

sealed class MyTasksScreenEffect {
    object StartLoading : MyTasksScreenEffect()
    object StopLoading : MyTasksScreenEffect()
    object TasksLoaded : MyTasksScreenEffect()
    data class NavigateToTaskScreen(val group: Group) : MyTasksScreenEffect()
}