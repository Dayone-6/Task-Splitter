package ru.dayone.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.dayone.auth.R
import ru.dayone.tasksplitter.common.theme.Typography

@Composable
fun AuthScreen(
    navController: NavHostController
) {
    val context = LocalContext.current

    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        snackbarHost = { snackBarHostState }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize().padding(innerPadding)
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
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                    beyondViewportPageCount = 2
                ) {
                    if (it == 0) {
                        EmailPasswordAuthScreen(
                            snackBarHostState
                        ){ email, password ->

                        }
                    } else {
                        PhoneAuthScreen(
                            onSendCode = {phone -> },
                            onConfirmCode = {confirmationCode -> }
                        )
                    }
                }
            }
        }
    }
}
