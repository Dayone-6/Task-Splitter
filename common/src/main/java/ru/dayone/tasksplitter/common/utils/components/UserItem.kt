package ru.dayone.tasksplitter.common.utils.components

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.theme.currentDarkScheme
import ru.dayone.tasksplitter.common.theme.currentLightScheme
import ru.dayone.tasksplitter.common.theme.currentScheme
import ru.dayone.tasksplitter.common.theme.primaryDark
import ru.dayone.tasksplitter.common.theme.primaryLight
import ru.dayone.tasksplitter.common.theme.secondaryDark
import ru.dayone.tasksplitter.common.theme.secondaryLight
import ru.dayone.tasksplitter.common.theme.surfaceBrightDark
import ru.dayone.tasksplitter.common.theme.surfaceBrightLight
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.or
import java.nio.file.WatchEvent

@Composable
fun UserItem(
    user: User,
    isSelected: Boolean = false,
    onItemClick: (user: User) -> Unit
) {
    val cornersRadius = remember { 30.dp }
    val outlineWidth = remember { 2.dp }
    Box(
        modifier = Modifier
            .padding(5.dp)
            .background(
                color = if (!isSelected) {
                    currentScheme.surfaceBright
                }else{
                    currentScheme.primary
                },
                shape = RoundedCornerShape(cornersRadius)
            )
            .fillMaxWidth(.9f)
            .clickable(enabled = true, onClick = {
                onItemClick(user)
            }),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .padding(outlineWidth)
                .background(
                    color = surfaceBrightDark.or(surfaceBrightLight),
                    shape = RoundedCornerShape(cornersRadius)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(0.dp)
            ) {
                Icon(
                    painter = ColorPainter(Color.Transparent),
                    contentDescription = "User color",
                    modifier = Modifier
                        .size(65.dp)
                        .background(
                            color = Color(user.color!!),
                            shape = RoundedCornerShape(cornersRadius)
                        ),
                    tint = Color.Green
                )
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .padding(end = 10.dp),
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
}

@Composable
@Preview
fun UserItemPreview(){
    UserItem(User("", "Leonid", "dayone", 1222222222), true) { }
}
