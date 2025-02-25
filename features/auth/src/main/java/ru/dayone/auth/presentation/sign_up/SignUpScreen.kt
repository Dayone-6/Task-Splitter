package ru.dayone.auth.presentation.sign_up

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.dayone.auth.R
import ru.dayone.auth.domain.model.RegistrationUser
import ru.dayone.auth.domain.model.User
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthState
import ru.dayone.auth.presentation.sign_up.state_hosting.SignUpAction
import ru.dayone.auth.presentation.sign_up.state_hosting.SignUpEffect
import ru.dayone.tasksplitter.common.navigation.MainNavRoute
import ru.dayone.tasksplitter.common.theme.Typography
import ru.dayone.tasksplitter.common.theme.buttonTextStyle
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.components.CustomTextField
import ru.dayone.tasksplitter.common.utils.components.LoadingDialog
import kotlin.random.Random

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel
) {
    val context = LocalContext.current

    var name by remember {
        mutableStateOf("")
    }

    var nickname by remember {
        mutableStateOf("")
    }

    val state by viewModel.state.collectAsState()

    var isLoading by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()

    val snackBarHostState = remember {
        SnackbarHostState()
    }


    LaunchedEffect(
        key1 = "effect"
    ) {
        viewModel.effect.collect {
            when (it) {
                is SignUpEffect.ToMain -> {
                    navController.navigate(MainNavRoute) {
                        popUpTo(0)
                    }
                }

                is SignUpEffect.StartLoading -> {
                    isLoading = true
                }

                is SignUpEffect.StopLoading -> {
                    isLoading = false
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        if (isLoading) {
            LoadingDialog()
        }
        if (state.error != null) {
            LaunchedEffect(state) {
                scope.launch {
                    snackBarHostState.showSnackbar(
                        message = state.error!!.getValue(context)
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = context.getString(R.string.title_sign_up),
                style = titleTextStyle,
                modifier = Modifier.padding(20.dp)
            )

            CustomTextField(
                text = name,
                hint = context.getString(R.string.hint_name),
                textStyle = Typography.titleLarge,
                onTextChanged = {
                    name = it
                }
            )

            CustomTextField(
                text = nickname,
                hint = context.getString(R.string.hint_nickname),
                textStyle = Typography.titleLarge,
                onTextChanged = {
                    nickname = it
                    viewModel.handleAction(SignUpAction.NicknameChanged(it))
                },
                isError = { state.nicknameError != null },
                supportingText = if (state.nicknameError != null) {
                    state.nicknameError!!.getValue(context)
                } else {
                    null
                }
            )

            Button(
                onClick = {
                    viewModel.handleAction(
                        SignUpAction.SignUp(
                            RegistrationUser(
                                name,
                                nickname,
                                Color(
                                    Random.nextInt(0, 256),
                                    Random.nextInt(0, 256),
                                    Random.nextInt(0, 256)
                                ).toArgb()
                            )
                        )
                    )
                }
            ) {
                Text(
                    text = context.getString(R.string.text_create_account),
                    style = buttonTextStyle
                )
            }
        }
    }
}