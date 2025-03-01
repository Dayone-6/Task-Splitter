package ru.dayone.main.account.presentation

import android.content.SharedPreferences
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.dayone.main.account.R
import ru.dayone.main.account.presentation.components.AccountMenuItem
import ru.dayone.main.account.presentation.components.AccountMenuItemUiModel
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.navigation.AccountNavRoutes
import ru.dayone.tasksplitter.common.theme.Typography
import ru.dayone.tasksplitter.common.theme.currentDarkScheme
import ru.dayone.tasksplitter.common.theme.currentLightScheme
import ru.dayone.tasksplitter.common.theme.errorDark
import ru.dayone.tasksplitter.common.theme.errorLight
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.getUser

@Composable
fun AccountScreen(
    navController: NavController,
    sharedPreferences: SharedPreferences,
    viewModel: AccountViewModel
) {
    val user = remember {
        sharedPreferences.getUser()
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .background(color = Color(user!!.color!!))
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.6f)
                .background(
                    color = if (isSystemInDarkTheme()) {
                        currentDarkScheme.background
                    } else {
                        currentLightScheme.background
                    }, shape = RoundedCornerShape(20.dp, 20.dp)
                )
                .padding(20.dp)
        ) {
            MainContent(navController, user)
        }
    }
}


@Composable
fun MainContent(navController: NavController, user: User) {
    val context = LocalContext.current

    val menuItems = arrayOf(
        AccountMenuItemUiModel(
            context.getString(R.string.text_friends),
            Icons.Filled.Group,
            AccountNavRoutes.Friends
        ),
        AccountMenuItemUiModel(
            context.getString(R.string.text_done_tasks),
            Icons.Filled.TaskAlt,
            AccountNavRoutes.Friends
        )
    )

    Row {
        Text(
            text = user.name!!,
            style = titleTextStyle,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "0",
            style = titleTextStyle,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(2f)
        )
    }
    Text(
        text = user.nickname!!,
        style = Typography.bodyLarge,
        modifier = Modifier.padding(top = 5.dp)
    )
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = Modifier.padding(top = 10.dp)
    ) {
        itemsIndexed(menuItems) { ind: Int, item: AccountMenuItemUiModel ->
            AccountMenuItem(
                item,
                isFirst = ind == 0,
                isLast = ind == menuItems.size - 1
            ) {
                navController.navigate(item.navigateTo)
            }
        }
    }

    val errorColor = if (isSystemInDarkTheme()) {
        errorDark
    } else {
        errorLight
    }
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier.fillMaxSize().padding(10.dp)
    ) {
        OutlinedButton(
            onClick = {

            },
            border = BorderStroke(
                2.dp,
                errorColor
            ),
            modifier = Modifier.fillMaxWidth(.7f)
        ) {
            Text(
                text = context.getString(R.string.text_sign_out),
                color = errorColor,
                style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
            )
        }
    }
}