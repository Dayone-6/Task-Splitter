package ru.dayone.main.account.presentation.completed_tasks

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun CompletedTasksScreen(
    navController: NavController,
    viewModel: CompletedTasksViewModel
){
    Text("Completed Tasks")
}