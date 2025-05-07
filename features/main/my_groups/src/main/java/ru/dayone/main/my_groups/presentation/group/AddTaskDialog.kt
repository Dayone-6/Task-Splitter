package ru.dayone.main.my_groups.presentation.group

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import ru.dayone.main.my_groups.R
import ru.dayone.main.my_groups.presentation.group.state_hosting.GroupAction
import ru.dayone.tasksplitter.common.theme.buttonTextStyle
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.components.CustomTextField
import ru.dayone.tasksplitter.common.utils.components.DefaultTopAppBar
import ru.dayone.tasksplitter.common.utils.defaultDialog

@Composable
fun AddTaskDialog(viewModel: GroupViewModel, groupId: String, onDismiss: () -> Unit) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.defaultDialog(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultTopAppBar(title = stringResource(R.string.title_new_task))
            CustomTextField(
                text = title,
                hint = stringResource(R.string.hint_title),
                onTextChanged = {
                    title = it
                },
                textStyle = titleTextStyle.copy(fontSize = 18.sp),
                modifier = Modifier.padding(top = 15.dp)
            )

            CustomTextField(
                text = description,
                hint = stringResource(R.string.hint_description),
                onTextChanged = {
                    description = it
                },
                textStyle = titleTextStyle.copy(fontSize = 18.sp),
                modifier = Modifier.padding(top = 5.dp, bottom = 15.dp)
            )


            Button(
                onClick = {
                    viewModel.handleAction(GroupAction.CreateTask(groupId, title, description))
                    onDismiss.invoke()
                },
                enabled = title.length > 3 && description.length > 3
            ) {
                Text(
                    text = stringResource(R.string.text_create),
                    style = buttonTextStyle
                )
            }
        }
    }
}