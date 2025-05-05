package ru.dayone.main.my_groups.presentation.group.state_hosting


sealed class GroupEffect {
    object StartLoading : GroupEffect()
    object StopLoading : GroupEffect()
    object UserAdded : GroupEffect()
}