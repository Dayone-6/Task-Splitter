package ru.dayone.main.my_groups.presentation

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
import androidx.compose.ui.window.Dialog
import ru.dayone.main.my_groups.R
import ru.dayone.main.my_groups.presentation.state_hosting.MyGroupsAction
import ru.dayone.tasksplitter.common.theme.backgroundDark
import ru.dayone.tasksplitter.common.theme.backgroundLight
import ru.dayone.tasksplitter.common.utils.components.CustomTextField
import ru.dayone.tasksplitter.common.utils.or

@Composable
fun CreateGroupDialog(
    viewModel: MyGroupsViewModel,
    onDismiss: () -> Unit
){
    var name by remember {
        mutableStateOf("")
    }
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = backgroundDark.or(backgroundLight),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            CustomTextField(
                text = name,
                hint = stringResource(R.string.hint_title),
                onTextChanged = {
                    name = it
                }
            )

            Button(
                onClick = {
                    viewModel.handleAction(MyGroupsAction.CreateGroup(name))
                    onDismiss.invoke()
                },
                enabled = name.length > 3
            ) {
                Text(stringResource(R.string.text_create))
            }
        }
    }
}