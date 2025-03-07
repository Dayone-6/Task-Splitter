package ru.dayone.main.account.presentation.account

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.dayone.main.account.presentation.account.state_hosting.AccountAction
import ru.dayone.main.account.presentation.account.state_hosting.AccountEffect
import ru.dayone.tasksplitter.common.navigation.AuthNavRoutes
import ru.dayone.tasksplitter.common.theme.currentDarkScheme
import ru.dayone.tasksplitter.common.theme.currentLightScheme
import ru.dayone.tasksplitter.common.utils.components.LoadingDialog
import ru.dayone.tasksplitter.common.utils.or

@Composable
fun AccountScreen(
    outerNavController: NavController,
    navController: NavController,
    viewModel: AccountViewModel,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    var isLoading by remember {
        mutableStateOf(false)
    }

    if (state.user != null) {
        LaunchedEffect(
            key1 = "getPoints"
        ) {
            viewModel.handleAction(AccountAction.RequestPoints(state.user!!.id))
        }
    }

    LaunchedEffect(
        key1 = "getUser"
    ) {
        viewModel.handleAction(AccountAction.GetUser())
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

    if (state.user != null) {
        Box(
            contentAlignment = Alignment.BottomCenter,
            modifier = Modifier
                .background(color = Color(state.user!!.color!!))
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
                        color = currentDarkScheme.background.or(currentLightScheme.background),
                        shape = RoundedCornerShape(20.dp, 20.dp)
                    )
                    .padding(20.dp)
            ) {
                MainContent(navController, viewModel, state)
            }
        }
    }
}