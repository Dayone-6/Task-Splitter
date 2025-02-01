package ru.dayone.auth.presentation.sign_in

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.dayone.auth.R
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthEffect
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthState
import ru.dayone.tasksplitter.common.navigation.AuthNavRoute
import ru.dayone.tasksplitter.common.navigation.MainNavRoute
import ru.dayone.tasksplitter.common.navigation.SignUpNavRoute
import ru.dayone.tasksplitter.common.theme.Typography
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.components.LoadingDialog

@Composable
fun AuthScreen(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    val context = LocalContext.current

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    var isLoading by remember {
        mutableStateOf(false)
    }

    val state by viewModel.state.collectAsState()

    val scope = rememberCoroutineScope()

    LaunchedEffect(
        key1 = "effect"
    ) {
        viewModel.effect.collect {
            when (it) {
                is AuthEffect.StartLoading -> {
                    isLoading = true
                }

                is AuthEffect.StopLoading -> {
                    isLoading = false
                }

                is AuthEffect.ToMain -> {
                    navController.navigate(MainNavRoute) {
                        popUpTo(0)
                    }
                }

                is AuthEffect.ToSignUp -> {
                    navController.navigate(SignUpNavRoute) {
                        popUpTo(0)
                    }
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->

        if ((state as AuthState.Content).error != null) {
            Log.d("AuthScreen", state.toString())
            LaunchedEffect(state) {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = (state as AuthState.Content).error!!.getValue(context)
                    )
                }
            }
        }

        if (isLoading) {
            LoadingDialog()
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Text(
                text = context.getString(R.string.title_sign_in_or_sign_up),
                style = titleTextStyle,
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = context.getString(R.string.text_swipe_form_to_change_registration_type),
                modifier = Modifier.padding(10.dp),
                style = Typography.titleLarge.copy(fontSize = 22.sp),
                textAlign = TextAlign.Center
            )

            AuthMainContent(viewModel, state, snackBarHostState)
        }
    }
}