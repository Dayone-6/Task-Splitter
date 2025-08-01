package ru.dayone.main.my_groups.presentation.my_groups

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import ru.dayone.main.my_groups.presentation.my_groups.state_hosting.MyGroupsAction
import ru.dayone.tasksplitter.common.theme.buttonTextStyle
import ru.dayone.tasksplitter.common.theme.currentScheme
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.components.CustomTextField
import ru.dayone.tasksplitter.common.utils.components.DefaultTopAppBar
import ru.dayone.tasksplitter.common.utils.defaultDialog

@Composable
fun CreateGroupDialog(
    viewModel: MyGroupsViewModel,
    onDismiss: () -> Unit
) {
    var name by remember {
        mutableStateOf("")
    }
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.defaultDialog(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultTopAppBar(title = stringResource(R.string.title_new_group))
            CustomTextField(
                text = name,
                hint = stringResource(R.string.hint_title),
                onTextChanged = {
                    name = it
                },
                textStyle = titleTextStyle.copy(fontSize = 20.sp),
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )

            Button(
                onClick = {
                    viewModel.handleAction(MyGroupsAction.CreateGroup(name))
                    onDismiss.invoke()
                },
                enabled = name.length > 3
            ) {
                Text(
                    text = stringResource(R.string.text_create),
                    style = buttonTextStyle
                )
            }
        }
    }
}