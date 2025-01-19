package ru.dayone.auth.presentation.sign_in

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import ru.dayone.auth.R
import ru.dayone.auth.domain.AuthType
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthAction
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthEffect
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthState
import ru.dayone.tasksplitter.common.navigation.MainNavRoute
import ru.dayone.tasksplitter.common.navigation.SignUpNavRoute
import ru.dayone.tasksplitter.common.theme.Typography
import ru.dayone.tasksplitter.common.theme.backgroundDark
import ru.dayone.tasksplitter.common.theme.backgroundLight
import ru.dayone.tasksplitter.common.theme.buttonTextStyle

@Composable
fun AuthScreen(
    navController: NavHostController,
    viewModel: AuthViewModel
) {
    val context = LocalContext.current

    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )
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
                        popUpTo<MainNavRoute> {
                            inclusive = true
                        }
                    }
                }

                is AuthEffect.ToSignUp -> {
                    navController.navigate(SignUpNavRoute) {
                        popUpTo<SignUpNavRoute> {
                            inclusive = true
                        }
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

        if((state as AuthState.Content).error != null){
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
            Dialog(
                onDismissRequest = { isLoading = false },
                properties = DialogProperties(
                    dismissOnClickOutside = false,
                    dismissOnBackPress = false
                )
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            shape = RoundedCornerShape(10.dp),
                            color = if (isSystemInDarkTheme()) {
                                backgroundDark
                            } else {
                                backgroundLight
                            }
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            Text(
                text = context.getString(R.string.title_sign_in_or_sign_up),
                style = Typography.titleLarge.copy(fontWeight = FontWeight.Bold, fontSize = 25.sp),
                modifier = Modifier.padding(20.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = context.getString(R.string.text_swipe_form_to_change_registration_type),
                modifier = Modifier.padding(10.dp),
                style = Typography.titleLarge.copy(fontSize = 22.sp),
                textAlign = TextAlign.Center
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                    beyondViewportPageCount = 2
                ) {
                    if (it == 0) {
                        EmailPasswordAuthScreen(
                            snackBarHostState,
                            passwordError = if((state as AuthState.Content).passwordError != null){
                                (state as AuthState.Content).passwordError!!.getValue(context)
                            }else{
                                null
                            },
                            onAuth = { email, password ->
                                viewModel.handleAction(
                                    AuthAction.SignInUser(
                                        AuthType.EmailAndPassword(
                                            email,
                                            password
                                        )
                                    )
                                )
                            },
                            onPasswordChanged = {
                                viewModel.handleAction(AuthAction.OnPasswordChanged(it))
                            }
                        )
                    } else {
                        PhoneAuthScreen(
                            onSendCode = { phone ->
                                viewModel.handleAction(
                                    AuthAction.SignInUser(
                                        AuthType.Phone(
                                            phone,
                                            null
                                        )
                                    )
                                )
                            },
                            onConfirmCode = { phone, confirmationCode ->
                                viewModel.handleAction(
                                    AuthAction.SignInUser(
                                        AuthType.Phone(
                                            phone,
                                            confirmationCode
                                        )
                                    )
                                )
                            }
                        )
                    }
                }
            }

            OutlinedButton(
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .padding(10.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = context.getString(R.string.text_sign_in_with),
                        style = buttonTextStyle.copy(),
                        modifier = Modifier.padding(5.dp)
                    )

                    Icon(
                        painter = painterResource(R.drawable.ic_google),
                        contentDescription = "Google Icon",
                        modifier = Modifier.size(32.dp),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}
