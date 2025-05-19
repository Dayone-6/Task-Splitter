package ru.dayone.main.my_tasks.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dayone.main.my_tasks.data.models.VoteUI
import ru.dayone.tasksplitter.common.theme.currentScheme
import ru.dayone.tasksplitter.common.theme.titleTextStyle

@Composable
fun VoteItem(
    vote: VoteUI
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 30.dp, end = 30.dp)
            .background(color = currentScheme.surfaceBright, shape = RoundedCornerShape(10.dp)),
    ) {
        Text(
            text = vote.user.name!!,
            style = titleTextStyle.copy(fontSize = 18.sp),
            modifier = Modifier
                .weight(1f)
                .width(0.dp)
                .padding(10.dp)
        )
        Text(
            text = vote.score.toString(),
            style = titleTextStyle.copy(fontSize = 18.sp),
            modifier = Modifier.padding(10.dp)
        )
    }
}