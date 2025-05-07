package ru.dayone.tasksplitter.common.utils.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import ru.dayone.tasksplitter.common.theme.backgroundDark
import ru.dayone.tasksplitter.common.theme.backgroundLight
import ru.dayone.tasksplitter.common.theme.currentScheme
import ru.dayone.tasksplitter.common.utils.or

@Composable
fun LoadingDialog(){
    Dialog(
        onDismissRequest = {  },
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
                    color = currentScheme!!.background
                ),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator()
        }
    }
}