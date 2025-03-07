package ru.dayone.main.account.presentation.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import ru.dayone.main.account.R
import ru.dayone.main.account.presentation.account.components.AccountMenuItem
import ru.dayone.main.account.presentation.account.components.AccountMenuItemUiModel
import ru.dayone.main.account.presentation.account.state_hosting.AccountAction
import ru.dayone.main.account.presentation.account.state_hosting.AccountState
import ru.dayone.tasksplitter.common.navigation.AccountNavRoutes
import ru.dayone.tasksplitter.common.theme.Typography
import ru.dayone.tasksplitter.common.theme.backgroundDark
import ru.dayone.tasksplitter.common.theme.backgroundLight
import ru.dayone.tasksplitter.common.theme.errorDark
import ru.dayone.tasksplitter.common.theme.errorLight
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.or

@Composable
fun MainContent(
    navController: NavController,
    viewModel: AccountViewModel,
    state: AccountState
) {
    val context = LocalContext.current

    val menuItems = remember {
        arrayOf(
            AccountMenuItemUiModel(
                context.getString(R.string.text_friends),
                Icons.Filled.Group,
                AccountNavRoutes.Friends
            ),
            AccountMenuItemUiModel(
                context.getString(R.string.text_completed_tasks),
                Icons.Filled.TaskAlt,
                AccountNavRoutes.CompletedTasks
            )
        )
    }

    var showConfirmationDialog by remember {
        mutableStateOf(false)
    }

    if (showConfirmationDialog) {
        ConfirmationDialog(viewModel) { showConfirmationDialog = false }
    }

    Row {
        Text(
            text = state.user!!.name!!,
            style = titleTextStyle,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = (state.points ?: "Loading").toString(),
            style = titleTextStyle,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(2f)
        )
    }
    Text(
        text = state.user!!.nickname!!,
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

    val errorColor = errorDark.or(errorLight)
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        OutlinedButton(
            onClick = {
                showConfirmationDialog = true
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

@Composable
fun ConfirmationDialog(viewModel: AccountViewModel, onCloseDialog: () -> Unit) {
    val context = LocalContext.current
    Dialog(
        onDismissRequest = { onCloseDialog.invoke() }
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = backgroundDark.or(backgroundLight),
                    shape = RoundedCornerShape(20)
                )
                .padding(start = 40.dp, end = 40.dp, top = 30.dp, bottom = 30.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = context.getString(R.string.text_confirmation),
                style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 22.sp)
            )
            Column(
                modifier = Modifier.padding(5.dp)
            ) {
                Text(
                    text = context.getString(R.string.text_are_you_sure),
                    style = Typography.bodyLarge.copy(fontSize = 19.sp),
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        onClick = {
                            viewModel.handleAction(AccountAction.SignOut())
                            onCloseDialog.invoke()
                        },
                    ) {
                        Text(
                            text = context.getString(R.string.text_yes),
                            style = Typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        }
    }
}