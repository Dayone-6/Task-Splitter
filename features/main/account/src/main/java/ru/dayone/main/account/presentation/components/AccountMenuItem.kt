package ru.dayone.main.account.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dayone.tasksplitter.common.theme.Typography
import ru.dayone.tasksplitter.common.theme.surfaceBrightDark
import ru.dayone.tasksplitter.common.theme.surfaceBrightLight
import ru.dayone.tasksplitter.common.utils.or

data class AccountMenuItemUiModel(
    val text: String,
    val icon: ImageVector,
    val navigateTo: String
)

@Composable
fun AccountMenuItem(
    accountMenuUiItem: AccountMenuItemUiModel,
    isFirst: Boolean = false,
    isLast: Boolean = false,
    onClick: (navigateTo: String) -> Unit
) {
    val roundedCornersMaxSize = 10.dp
    val roundedCornersMinSize = 2.dp
    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .clickable {
                onClick.invoke(accountMenuUiItem.navigateTo)
            }
            .background(
                color = surfaceBrightDark.or(surfaceBrightLight),
                shape = RoundedCornerShape(
                    if (isFirst) {
                        roundedCornersMaxSize
                    } else {
                        roundedCornersMinSize
                    },
                    if (isFirst) {
                        roundedCornersMaxSize
                    } else {
                        roundedCornersMinSize
                    },
                    if (isLast) {
                        roundedCornersMaxSize
                    } else {
                        roundedCornersMinSize
                    },
                    if (isLast) {
                        roundedCornersMaxSize
                    } else {
                        roundedCornersMinSize
                    }
                )
            ),
    ) {
        Icon(
            imageVector = accountMenuUiItem.icon,
            contentDescription = accountMenuUiItem.text,
            modifier = Modifier
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                .size(30.dp)
        )
        Text(
            text = accountMenuUiItem.text,
            style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}