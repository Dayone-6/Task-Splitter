package ru.dayone.tasksplitter.common.navigation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem<T: Any>(
    val title: String,
    val navRoute: T,
    val icon: ImageVector
)