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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collect
import ru.dayone.main.account.R
import ru.dayone.main.account.presentation.components.AccountMenuItem
import ru.dayone.main.account.presentation.components.AccountMenuItemUiModel
import ru.dayone.main.account.presentation.state_hosting.AccountAction
import ru.dayone.main.account.presentation.state_hosting.AccountEffect
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.navigation.AccountNavRoutes
import ru.dayone.tasksplitter.common.navigation.AuthNavRoutes
import ru.dayone.tasksplitter.common.theme.Typography
import ru.dayone.tasksplitter.common.theme.backgroundDark
import ru.dayone.tasksplitter.common.theme.backgroundLight
import ru.dayone.tasksplitter.common.theme.currentDarkScheme
import ru.dayone.tasksplitter.common.theme.currentLightScheme
import ru.dayone.tasksplitter.common.theme.errorDark
import ru.dayone.tasksplitter.common.theme.errorLight
import ru.dayone.tasksplitter.common.theme.surfaceBrightDark
import ru.dayone.tasksplitter.common.theme.surfaceBrightLight
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.components.LoadingDialog
import ru.dayone.tasksplitter.common.utils.getUser

@Composable
fun AccountScreen(
    outerNavController: NavController,
    navController: NavController,
    sharedPreferences: SharedPreferences,
    viewModel: AccountViewModel,
    snackbarHostState: SnackbarHostState
) {
    val user = remember {
        sharedPreferences.getUser()
    }

    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    var isLoading by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(
        state.hashCode()
    ) {
        if (state.error != null) {
            snackbarHostState.showSnackbar(state.error!!.getValue(context))
        }
    }

    LaunchedEffect(
        key1 = "effect"
    ) {
        viewModel.effect.collect {
            when (it) {
                is AccountEffect.NavigateToSignIn -> {
                    outerNavController.navigate(AuthNavRoutes.SignIn) {
                        popUpTo(0)
                    }
                }

                is AccountEffect.StartLoading -> {
                    isLoading = true
                }

                is AccountEffect.StopLoading -> {
                    isLoading = false
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .background(color = Color(user!!.color!!))
            .fillMaxSize()
    ) {
        if (isLoading) {
            LoadingDialog()
        }
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
            MainContent(navController, user, viewModel)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(navController: NavController, user: User, viewModel: AccountViewModel) {
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

    var showConfirmationDialog by remember {
        mutableStateOf(false)
    }

    if (showConfirmationDialog) {
        Dialog(
            onDismissRequest = { showConfirmationDialog = false }
        ) {
            Column(
                modifier = Modifier.background(
                    color = if(isSystemInDarkTheme()){
                        surfaceBrightDark
                    }else{
                        surfaceBrightLight
                    },
                    shape = RoundedCornerShape(20)
                ).padding(40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = context.getString(R.string.text_are_you_sure),
                    style = titleTextStyle
                )
                Button(
                    onClick = {
                        viewModel.handleAction(AccountAction.SignOut())
                        showConfirmationDialog = false
                    },
                    modifier = Modifier.padding(5.dp)
                ) {
                    Text(
                        text = context.getString(R.string.text_yes),
                        style = Typography.bodyLarge
                    )
                }
            }
        }
    }

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