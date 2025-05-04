package ru.dayone.main.my_groups.presentation.state_hosting

sealed class MyGroupsEffect {
    object StartLoading : MyGroupsEffect()
    object StopLoading : MyGroupsEffect()
    object GroupCreated : MyGroupsEffect()
    object RequestedGroupsLoaded : MyGroupsEffect()
}