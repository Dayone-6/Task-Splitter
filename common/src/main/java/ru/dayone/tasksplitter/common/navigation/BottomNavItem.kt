package ru.dayone.tasksplitter.common.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val title: String,
    val navRoute: String,
    val icon: ImageVector
)