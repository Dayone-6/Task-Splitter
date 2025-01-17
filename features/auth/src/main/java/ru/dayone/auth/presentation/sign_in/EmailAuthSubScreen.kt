package ru.dayone.auth.presentation.sign_in

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ru.dayone.auth.R
import ru.dayone.tasksplitter.common.theme.Typography
import ru.dayone.tasksplitter.common.theme.buttonTextStyle
import ru.dayone.tasksplitter.common.utils.components.CustomTextField
import ru.dayone.tasksplitter.common.utils.components.EmailField

@Composable
fun EmailPasswordAuthScreen(
    snackBarHost: SnackbarHostState,
    onAuth: (email: String, password: String) -> Unit
) {
    var email by remember {
        mutableStateOf("")
    }
    var isEmailValid by remember {
        mutableStateOf(true)
    }
    var password by remember {
        mutableStateOf("")
    }
    var isPasswordHidden by remember {
        mutableStateOf(true)
    }

    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        EmailField(
            email = email,
            onEmailChanged = { emailString: String, isValid: Boolean ->
                email = emailString
                isEmailValid = isValid
            },
            textStyle = Typography.titleLarge
        )

        CustomTextField(
            text = password,
            hint = context.getString(R.string.hint_password),
            textStyle = Typography.titleLarge,
            onTextChanged = { password = it },
            visualTransformation = if (isPasswordHidden) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            trailingIcon = {
                val image = if (isPasswordHidden)
                    Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff

                val description = if (isPasswordHidden) "Hide password" else "Show password"

                IconButton(onClick = { isPasswordHidden = !isPasswordHidden }) {
                    Icon(imageVector = image, description)
                }
            }
        )

        Button(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .padding(10.dp),
            onClick = {
                if (isEmailValid && email.isNotBlank()) {
                    onAuth.invoke(email, password)
                } else {
                    scope.launch {
                        snackBarHost
                            .showSnackbar(
                                context.getString(R.string.error_incorrect_email)
                            )
                    }
                }
            }
        ) {
            Text(
                text = context.getString(R.string.text_continue),
                style = buttonTextStyle.copy(fontSize = 18.sp)
            )
        }
    }
}

@Preview
@Composable
fun EmailPasswordAuthScreenPreview() {
    EmailPasswordAuthScreen(SnackbarHostState(), onAuth = { _, _ -> })
}