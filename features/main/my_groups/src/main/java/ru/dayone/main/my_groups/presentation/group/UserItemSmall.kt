package ru.dayone.main.my_groups.presentation.group

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.theme.currentScheme
import ru.dayone.tasksplitter.common.theme.titleTextStyle

@Composable
fun UserItemSmall(user: User, isLast: Boolean, onItemClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(5.dp)
            .height(50.dp)
            .width(100.dp)
            .background(
                shape = RoundedCornerShape(15.dp),
                color = if (!isLast) {
                    Color(user.color!!)
                } else {
                    currentScheme.surfaceBright
                }
            )
    ) {
        if (isLast) {
            Icon(
                imageVector = Icons.Filled.PersonAdd,
                contentDescription = "Add member",
                modifier = Modifier.clickable(true, onClick = onItemClick)
            )
        } else {
            Text(
                text = user.name!!,
                modifier = Modifier.padding(10.dp),
                style = titleTextStyle.copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
            )
        }
    }
}