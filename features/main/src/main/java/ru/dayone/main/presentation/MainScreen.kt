package ru.dayone.main.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.dayone.main.R
import ru.dayone.main.account.presentation.AccountScreen
import ru.dayone.main.my_groups.presentation.MyGroupsScreen
import ru.dayone.main.my_tasks.presentation.MyTasksScreen
import ru.dayone.tasksplitter.common.navigation.AccountNavRoute
import ru.dayone.tasksplitter.common.navigation.BottomNavItem
import ru.dayone.tasksplitter.common.navigation.MyGroupsNavRoute
import ru.dayone.tasksplitter.common.navigation.MyTasksNavRoute

@Composable
fun MainScreen(
    outerNavController: NavHostController
){
    val innerNavController = rememberNavController()

    val context = LocalContext.current

    val navItems = remember{
        arrayOf(
            BottomNavItem(
                context.getString(R.string.title_account),
                AccountNavRoute,
                Icons.Outlined.AccountCircle
            ),
            BottomNavItem(
                context.getString(R.string.title_my_groups),
                MyGroupsNavRoute,
                Icons.Outlined.Face
            ),
            BottomNavItem(
                context.getString(R.string.title_my_tasks),
                MyTasksNavRoute,
                Icons.AutoMirrored.Outlined.List
            )
        )
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by innerNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                navItems.forEach { navItem ->
                    NavigationBarItem(
                        icon = { androidx.compose.material3.Icon(navItem.icon, contentDescription = navItem.title) },
                        label = { Text(navItem.title) },
                        selected = currentDestination?.hierarchy?.any { it.hasRoute(
                            navItem.navRoute::class
                        ) } == true,
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
            startDestination = MyGroupsNavRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<MyGroupsNavRoute> {
                MyGroupsScreen()
            }

            composable<MyTasksNavRoute> {
                MyTasksScreen()
            }

            composable<AccountNavRoute> {
                AccountScreen()
            }
        }
    }
}