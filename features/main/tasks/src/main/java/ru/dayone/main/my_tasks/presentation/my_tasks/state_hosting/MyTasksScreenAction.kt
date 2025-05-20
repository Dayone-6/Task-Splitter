package ru.dayone.main.my_tasks.presentation.my_tasks.state_hosting

sealed class MyTasksScreenAction {
    class LoadTasks : MyTasksScreenAction()
    class GetGroupById(val groupId: String) : MyTasksScreenAction()
}