package ru.dayone.main.my_groups.presentation.state_hosting

sealed class MyGroupsAction {
    class GetGroups(val requireNew: Boolean = false) : MyGroupsAction()
    class CreateGroup(val name: String) : MyGroupsAction()
}