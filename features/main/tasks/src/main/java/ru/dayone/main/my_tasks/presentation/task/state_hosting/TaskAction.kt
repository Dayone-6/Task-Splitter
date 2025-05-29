package ru.dayone.main.my_tasks.presentation.task.state_hosting

sealed class TaskAction {
    class LoadVotes(val taskId: String, val requireNew: Boolean = false) : TaskAction()
    class Vote(val taskId: String, val vote: Int) : TaskAction()
    class EndTask(val taskId: String) : TaskAction()
    class LoadCurrentUser : TaskAction()
    class PayForTask(val taskId: String) : TaskAction()
    class LoadUser(val userId: String) : TaskAction()
}