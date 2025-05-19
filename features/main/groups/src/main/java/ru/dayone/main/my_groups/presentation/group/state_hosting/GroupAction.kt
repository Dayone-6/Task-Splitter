package ru.dayone.main.my_groups.presentation.group.state_hosting

import ru.dayone.main.my_groups.data.network.models.GroupMember

sealed class GroupAction {
    class GetTasks(val groupId: String, val requireNew: Boolean = false) : GroupAction()
    class GetUsersFromGroup(val groupId: String) : GroupAction()
    class AddUserToGroup(val userId: String, val groupId: String) : GroupAction()
    class GetUserFriends() : GroupAction()
    class RefreshMembersAndTasks(val groupId: String) : GroupAction()
    class GetCurrentUser() : GroupAction()
    class CreateTask(val groupId: String, val title: String, val description: String) : GroupAction()
}