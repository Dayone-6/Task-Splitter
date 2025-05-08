package ru.dayone.main.my_groups.presentation.group

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.tasksplitter.common.theme.Typography
import ru.dayone.tasksplitter.common.theme.currentScheme
import ru.dayone.tasksplitter.common.theme.successColorDark
import ru.dayone.tasksplitter.common.theme.successColorLight
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.or

@Composable
fun TaskItem(task: Task, onItemClick: () -> Unit) {
    val backgroundColor = when (task.status) {
        0 -> {
            currentScheme.errorContainer
        }

        1 -> {
            successColorDark.or(successColorLight)
        }

        else -> {
            currentScheme.surfaceBright
        }
    }
    Box(
        modifier = Modifier
            .padding(5.dp)
            .background(
                shape = RoundedCornerShape(5.dp),
                color = backgroundColor
            )
            .clickable(true, onClick = onItemClick)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = task.title,
                style = titleTextStyle.copy(fontSize = 18.sp),
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = task.description,
                style = Typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun TaskItemPreview() {
    TaskItem(
        Task(
            "",
            "",
            "Test Test Test",
            "Description Description Description Description Description Description Description Description Description",
            "",
            2
        )
    ) { }
}