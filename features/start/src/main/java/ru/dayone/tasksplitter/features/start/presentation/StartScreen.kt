package ru.dayone.tasksplitter.features.start.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.navOptions
import ru.dayone.tasksplitter.common.navigation.LoginNavRoute

@Composable
fun StartScreen(
    navController: NavHostController
) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Start Screen",
            textAlign = TextAlign.Center
        )
        Button(
            onClick = {
                navController.navigate(
                    route = LoginNavRoute
                )
            }
        ) {
            Text("To Login")
        }
    }
}