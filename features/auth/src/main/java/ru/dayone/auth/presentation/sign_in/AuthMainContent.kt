package ru.dayone.auth.presentation.sign_in

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import ru.dayone.auth.R
import ru.dayone.auth.domain.AuthType
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthAction
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthEffect
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthState
import ru.dayone.tasksplitter.common.theme.buttonTextStyle
import ru.dayone.tasksplitter.common.utils.UIText
import java.util.concurrent.TimeUnit

@Composable
fun AuthMainContent(
    viewModel: AuthViewModel,
    state: AuthState,
    snackBarHostState: SnackbarHostState
) {
    val context = LocalContext.current

    val pagerState = rememberPagerState(
        pageCount = { 2 }
    )

    var verificationId by remember {
        mutableStateOf<String?>(null)
    }
    var verificationCodeRefreshToken by remember {
        mutableStateOf<PhoneAuthProvider.ForceResendingToken?>(null)
    }

    val phoneAuthCallbacks = remember {
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(creds: PhoneAuthCredential) {
                viewModel.changeEffect(AuthEffect.StopLoading)

                Log.d("AuthMainContent", "Phone Verification Completed")
                viewModel.handleAction(
                    AuthAction.SignInUser(
                        AuthType.Phone(
                            creds
                        )
                    )
                )
            }

            override fun onVerificationFailed(error: FirebaseException) {
                viewModel.changeEffect(AuthEffect.StopLoading)

                val nowState = state as AuthState.Content
                Log.e("AuthMainContent", "PhoneAuth", error)
                viewModel.changeState(
                    AuthState.Content(
                        user = nowState.user,
                        error = UIText.StringResource(R.string.error_something_went_wrong),
                        passwordError = nowState.passwordError,
                        isVerificationCodeError = nowState.isVerificationCodeError
                    )
                )
            }

            override fun onCodeSent(
                id: String,
                resendToken: PhoneAuthProvider.ForceResendingToken
            ) {
                viewModel.changeEffect(AuthEffect.StopLoading)

                verificationId = id
                verificationCodeRefreshToken = resendToken
            }
        }
    }

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
                    passwordError = if ((state as AuthState.Content).passwordError != null) {
                        state.passwordError!!.getValue(context)
                    } else {
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
                        viewModel.changeEffect(AuthEffect.StartLoading)
                        Log.d("AuthMainContent", phone)
                        val options = PhoneAuthOptions.newBuilder()
                            .setActivity(context.findActivity()!!)
                            .setPhoneNumber("+7$phone")
                            .setCallbacks(phoneAuthCallbacks)
                            .setTimeout(60L, TimeUnit.SECONDS)
                            .build()
                        PhoneAuthProvider.verifyPhoneNumber(options)
                    },
                    onConfirmCode = { _, confirmationCode ->
                        viewModel.changeEffect(AuthEffect.StartLoading)

                        if (verificationId != null) {
                            val credential =
                                PhoneAuthProvider.getCredential(verificationId!!, confirmationCode)
                            viewModel.handleAction(
                                AuthAction.SignInUser(
                                    AuthType.Phone(
                                        credential
                                    )
                                )
                            )
                        }
                    },
                    isVerificationCodeError = (state as AuthState.Content).isVerificationCodeError
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

private fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}