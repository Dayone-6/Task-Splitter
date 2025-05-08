package ru.dayone.main.my_groups.presentation.my_groups.state_hosting

import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.tasksplitter.common.utils.UIText

data class MyGroupsState (
    var groups: List<Group>? = null,
    var error: UIText? = null
)