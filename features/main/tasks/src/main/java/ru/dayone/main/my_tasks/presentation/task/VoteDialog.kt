package ru.dayone.main.my_tasks.presentation.task

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.dayone.main.my_tasks.R
import ru.dayone.tasksplitter.common.theme.buttonTextStyle
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.defaultDialog

@Composable
fun VoteDialog(onVote: (vote: Int) -> Unit, onDismiss: () -> Unit) {
    var rate by remember { mutableFloatStateOf(0f) }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier.defaultDialog()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.title_rate_difficult_of_this_task),
                    style = titleTextStyle.copy(fontSize = 20.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
                )

                Slider(
                    value = rate,
                    onValueChange = { rate = it },
                    steps = 100,
                    valueRange = 0f..100f,
                    modifier = Modifier.padding(
                        top = 15.dp,
                        bottom = 15.dp,
                        start = 10.dp,
                        end = 10.dp
                    )
                )

                Button(
                    onClick = { onVote.invoke(rate.toInt()) },
                    modifier = Modifier.padding(10.dp),
                    enabled = rate > 0f
                ) {
                    Text(
                        text = stringResource(R.string.text_vote),
                        style = buttonTextStyle,
                    )
                }
            }
        }
    }
}