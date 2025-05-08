package ru.dayone.main.my_groups.presentation.my_groups.state_hosting

sealed class MyGroupsAction {
    class GetGroups(val requireNew: Boolean = false) : MyGroupsAction()
    class CreateGroup(val name: String) : MyGroupsAction()
}