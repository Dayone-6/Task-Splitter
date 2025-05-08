package ru.dayone.main.my_groups.presentation.group.state_hosting


sealed class GroupEffect {
    object StartLoading : GroupEffect()
    object StopLoading : GroupEffect()
    object TaskCreated : GroupEffect()
    object RequiredTasksLoaded : GroupEffect()
    object UserAdded : GroupEffect()
}