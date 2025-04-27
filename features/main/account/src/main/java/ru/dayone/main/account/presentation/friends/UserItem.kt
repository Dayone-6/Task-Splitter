package ru.dayone.main.account.presentation.friends

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.theme.surfaceBrightDark
import ru.dayone.tasksplitter.common.theme.surfaceBrightLight
import ru.dayone.tasksplitter.common.theme.surfaceDark
import ru.dayone.tasksplitter.common.theme.surfaceLight
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.or

@Composable
fun UserItem(
    user: User,
    onItemClick: (user: User) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .background(
                color = surfaceBrightDark.or(surfaceBrightLight),
                shape = RoundedCornerShape(5.dp)
            )
            .fillMaxWidth(.9f)
            .clickable(enabled = true, onClick = {
                onItemClick(user)
            }),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Icon(
                painter = ColorPainter(Color.Transparent),
                contentDescription = "User color",
                modifier = Modifier
                    .size(65.dp)
                    .background(
                        color = Color(user.color!!),
                        shape = RoundedCornerShape(50.dp)
                    ),
                tint = Color.Green
            )
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(end = 15.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = user.name!!,
                    style = titleTextStyle.copy(fontSize = 20.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = user.nickname!!,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
@Preview
fun UserItemPreview() {
    UserItem(User("saldkfj", "Leonid", "testest", Color(100, 200, 50).toArgb()), {})
}