package ru.dayone.main.my_tasks.presentation.task.state_hosting

sealed class TaskEffect {
    object StartLoading : TaskEffect()
    object StopLoading : TaskEffect()
    object VoteSucceed : TaskEffect()
    object EndTaskSucceed : TaskEffect()
    object VotesLoaded : TaskEffect()
}