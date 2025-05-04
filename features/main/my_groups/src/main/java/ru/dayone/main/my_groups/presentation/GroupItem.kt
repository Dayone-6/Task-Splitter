package ru.dayone.main.my_groups.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.tasksplitter.common.theme.surfaceBrightDark
import ru.dayone.tasksplitter.common.theme.surfaceBrightLight
import ru.dayone.tasksplitter.common.utils.or

@Composable
fun GroupItem(group: Group) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .background(
                shape = RoundedCornerShape(15.dp),
                color = surfaceBrightDark.or(surfaceBrightLight)
            )
            .fillMaxWidth(.9f),
        contentAlignment = Alignment.Center
    ) {
        Text(text = group.name, modifier = Modifier.padding(10.dp))
    }
}