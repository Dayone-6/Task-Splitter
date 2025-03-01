package ru.dayone.tasksplitter.common.navigation

import kotlinx.serialization.Serializable

@Serializable
data object StartNavRoutes{
    const val Start = "start"
}

@Serializable
data object AuthNavRoutes{
    const val SignIn = "signIn"
    const val SignUp = "signUp"
}

@Serializable
data object MainNavRoutes{
    const val Main = "main"
}

@Serializable
data object MyTasksNavRoutes{
    const val Route = "myTasksNavRoute"
    const val MyTasks = "myTasks"
}

@Serializable
data object MyGroupsNavRoutes{
    const val Route = "myGroupsNavRoute"
    const val MyGroups = "myGroups"
}

@Serializable
data object AccountNavRoutes{
    const val Route = "accountNavRoute"
    const val Account = "myAccount"
    const val Friends = "friends"
}

