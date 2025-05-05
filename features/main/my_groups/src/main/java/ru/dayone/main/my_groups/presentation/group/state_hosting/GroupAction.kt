package ru.dayone.main.my_groups.presentation.group.state_hosting

sealed class GroupAction {
    class GetTasks(val groupId: String, val requireNew: Boolean? = null) : GroupAction()
}