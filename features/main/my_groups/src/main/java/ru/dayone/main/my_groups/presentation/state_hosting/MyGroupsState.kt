package ru.dayone.main.my_groups.presentation.state_hosting

import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.tasksplitter.common.utils.UIText

open class MyGroupsState() {
    val groups: List<Group>? = null
    val error: UIText? = null
}