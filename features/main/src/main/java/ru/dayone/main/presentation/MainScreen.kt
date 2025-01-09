package ru.dayone.main.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController

@Composable
fun MainScreen(
    navController: NavHostController
){
    Column {
        Text(
            modifier = Modifier.fillMaxHeight(),
            text = "Main Screen",
            textAlign = TextAlign.Center
        )
    }
}