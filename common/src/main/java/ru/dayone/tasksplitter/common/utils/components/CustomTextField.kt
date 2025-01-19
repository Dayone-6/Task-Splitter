package ru.dayone.tasksplitter.common.utils.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    text: String,
    hint: String,
    modifier: Modifier = Modifier,
    isCustomModifier: Boolean = false,
    prefix: String? = null,
    supportingText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: () -> Boolean = { false },
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    onTextChanged: (String) -> Unit,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = text,
        onValueChange = {
            onTextChanged.invoke(it)
        },
        modifier = if (!isCustomModifier) {
            modifier
                .fillMaxWidth(.9f)
                .padding(5.dp)
        } else {
            modifier
        },
        label = {
            Text(
                text = hint,
                style = textStyle.copy(fontSize = textStyle.fontSize / 1.4)
            )
        },
        prefix = if (!prefix.isNullOrBlank()) {
            { Text(text = prefix, style = textStyle) }
        } else {
            null
        },
        supportingText = if (!supportingText.isNullOrBlank()) {
            { Text(text = supportingText, style = textStyle.copy(fontSize = textStyle.fontSize / 1.5)) }
        } else {
            null
        },
        keyboardOptions = keyboardOptions,
        shape = Shapes().medium,
        textStyle = textStyle,
        isError = isError.invoke(),
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation
    )
}