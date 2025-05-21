package ru.dayone.main.my_tasks.presentation.my_tasks.state_hosting

sealed class MyTasksScreenAction {
    class LoadTasks(val isCompleted: Boolean = false) : MyTasksScreenAction()
    class GetGroupById(val groupId: String) : MyTasksScreenAction()
}