package ru.dayone.main.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import ru.dayone.main.R
import ru.dayone.main.account.presentation.account.AccountScreen
import ru.dayone.main.account.presentation.completed_tasks.CompletedTasksScreen
import ru.dayone.main.account.presentation.friends.FriendsScreen
import ru.dayone.main.account.presentation.settings.SettingsScreen
import ru.dayone.main.data.di.MainComponent
import ru.dayone.main.my_groups.presentation.MyGroupsScreen
import ru.dayone.main.my_tasks.presentation.MyTasksScreen
import ru.dayone.tasksplitter.common.navigation.AccountNavRoutes
import ru.dayone.tasksplitter.common.navigation.BottomNavItem
import ru.dayone.tasksplitter.common.navigation.MyGroupsNavRoutes
import ru.dayone.tasksplitter.common.navigation.MyTasksNavRoutes

@Composable
fun MainScreen(
    outerNavController: NavHostController,
    mainComponent: MainComponent
) {
    val innerNavController = rememberNavController()

    val context = LocalContext.current

    val navItems = remember {
        arrayOf(
            BottomNavItem(
                context.getString(R.string.title_account),
                AccountNavRoutes.ROUTE,
                Icons.Outlined.AccountCircle
            ),
            BottomNavItem(
                context.getString(R.string.title_my_groups),
                MyGroupsNavRoutes.ROUTE,
                Icons.Outlined.Face
            ),
            BottomNavItem(
                context.getString(R.string.title_my_tasks),
                MyTasksNavRoutes.ROUTE,
                Icons.AutoMirrored.Outlined.List
            )
        )
    }

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                navItems.forEach { navItem ->
                    NavigationBarItem(
                        icon = {
                            androidx.compose.material3.Icon(
                                navItem.icon,
                                contentDescription = navItem.title
                            )
                        },
                        label = { Text(navItem.title) },
                        selected = currentDestination?.hierarchy?.any {
                            it.route == navItem.navRoute
                        } == true,
                        onClick = {
                            innerNavController.navigate(navItem.navRoute) {
                                popUpTo(innerNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            innerNavController,
            startDestination = MyGroupsNavRoutes.ROUTE,
            modifier = Modifier.padding(innerPadding)
        ) {
            navigation(
                route = MyGroupsNavRoutes.ROUTE,
                startDestination = MyGroupsNavRoutes.MY_GROUPS
            ) {
                composable(MyGroupsNavRoutes.MY_GROUPS) {
                    MyGroupsScreen()
                }
            }

            navigation(
                route = MyTasksNavRoutes.ROUTE,
                startDestination = MyTasksNavRoutes.MY_TASKS
            ) {
                composable(MyTasksNavRoutes.MY_TASKS) {
                    MyTasksScreen()
                }
            }

            navigation(
                route = AccountNavRoutes.ROUTE,
                startDestination = AccountNavRoutes.ACCOUNT
            ) {
                composable(AccountNavRoutes.ACCOUNT) {
                    AccountScreen(
                        outerNavController,
                        innerNavController,
                        mainComponent.getAccountViewModel(),
                        snackbarHostState
                    )
                }

                composable(AccountNavRoutes.FRIENDS) {
                    FriendsScreen(
                        innerNavController,
                        mainComponent.getFriendsViewModel()
                    )
                }

                composable(AccountNavRoutes.COMPLETED_TASKS) {
                    CompletedTasksScreen(
                        innerNavController,
                        mainComponent.getCompletedTasksViewModel()
                    )
                }

                composable(AccountNavRoutes.SETTINGS) {
                    SettingsScreen(
                        innerNavController,
                        mainComponent.getSettingsPrefs()
                    )
                }
            }
        }
    }
}