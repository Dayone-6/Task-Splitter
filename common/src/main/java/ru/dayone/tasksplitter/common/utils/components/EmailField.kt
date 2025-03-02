package ru.dayone.tasksplitter.common.utils.components

import android.util.Patterns
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import ru.dayone.tasksplitter.common.R

@Composable
fun EmailField(
    email: String,
    modifier: Modifier = Modifier,
    isCustomModifier: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    onEmailChanged: (String, Boolean) -> Unit
) {
    var isEmailValid by remember {
        mutableStateOf(true)
    }
    CustomTextField(
        text = email,
        hint = LocalContext.current.getString(R.string.hint_email),
        onTextChanged = {
            isEmailValid = Patterns.EMAIL_ADDRESS.matcher(it).matches()
            onEmailChanged.invoke(it, isEmailValid)
        },
        modifier = modifier,
        textStyle = textStyle,
        isCustomModifier = isCustomModifier,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
        isError = { !isEmailValid }
    )
}