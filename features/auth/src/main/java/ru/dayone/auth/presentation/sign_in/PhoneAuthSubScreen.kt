package ru.dayone.auth.presentation.sign_in

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.dayone.auth.R
import ru.dayone.tasksplitter.common.theme.Typography
import ru.dayone.tasksplitter.common.theme.buttonTextStyle
import ru.dayone.tasksplitter.common.utils.components.CustomTextField
import ru.dayone.tasksplitter.common.utils.components.PhoneNumberField

@Composable
fun PhoneAuthScreen(
    onSendCode: (phone: String) -> Unit,
    onConfirmCode: (phone: String, code: String) -> Unit
) {
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var confirmationCode by remember {
        mutableStateOf("")
    }
    var confirmationCodeFieldIsVisible by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth(),
    ) {
        PhoneNumberField(
            phone = phoneNumber,
            textStyle = Typography.titleLarge,

        ) {
            phoneNumber = it
        }

        AnimatedVisibility(
            visible = confirmationCodeFieldIsVisible,
            enter = slideInVertically()
                    + expandVertically(expandFrom = Alignment.Top)
                    + fadeIn(initialAlpha = 0.3f)
        ) {
            CustomTextField(
                text = confirmationCode,
                onTextChanged = { confirmationCode = it },
                hint = context.getString(R.string.hint_confirmation_code),
                textStyle = Typography.titleLarge
            )
        }

        Button(
            modifier = Modifier.fillMaxWidth(.8f).padding(10.dp),
            onClick = {
                if(!confirmationCodeFieldIsVisible){
                    confirmationCodeFieldIsVisible = true
                    onSendCode.invoke(phoneNumber)
                }else{
                    onConfirmCode.invoke(phoneNumber, confirmationCode)
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