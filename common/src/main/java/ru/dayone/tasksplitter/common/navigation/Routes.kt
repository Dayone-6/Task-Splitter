package ru.dayone.tasksplitter.common.navigation

import kotlinx.serialization.Serializable

data object StartNavRoutes {
    const val START = "start"
}

data object AuthNavRoutes {
    const val SIGN_IN = "signIn"
    const val SIGN_UP = "signUp"
}

data object MainNavRoutes {
    const val MAIN = "main"
}

data object TasksNavRoutes {
    const val ROUTE = "myTasksNavRoute"
    const val MY_TASKS = "myTasks"

    @Serializable
    data class TASK(val task: String, val groupCreatorId: String)
}

data object MyGroupsNavRoutes {
    const val ROUTE = "myGroupsNavRoute"
    const val MY_GROUPS = "myGroups"

    @Serializable
    data class GROUP(
        val groupJson: String
    )

    @Serializable
    data class TASK(val task: String, val groupCreatorId: String)
}

data object AccountNavRoutes {
    const val ROUTE = "accountNavRoute"
    const val ACCOUNT = "myAccount"
    const val FRIENDS = "friends"
    const val COMPLETED_TASKS = "completedTasks"
    const val SETTINGS = "settings"
}

