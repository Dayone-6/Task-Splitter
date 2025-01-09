package ru.dayone.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.traceEventStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import ru.dayone.tasksplitter.common.navigation.LoginNavRoute
import ru.dayone.tasksplitter.common.navigation.MainNavRoute
import ru.dayone.tasksplitter.common.navigation.StartNavRoute

@Composable
fun LoginScreen(
    navController: NavHostController
){
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login Screen",
            textAlign = TextAlign.Center
        )
        Button(
            onClick = {
                navController.navigate(
                    route = MainNavRoute,
                    navOptions = navOptions {
                        popUpTo<LoginNavRoute>{ saveState = true }
                        restoreState = true
                    }
                )
            }
        ) {
            Text("To Main")
        }
    }
}