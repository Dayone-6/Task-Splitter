package ru.dayone.main.my_groups.presentation.group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
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
import ru.dayone.main.my_groups.presentation.group.state_hosting.GroupAction
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.utils.components.DefaultTopAppBar
import ru.dayone.tasksplitter.common.utils.components.UserItem
import ru.dayone.tasksplitter.common.utils.defaultDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemberDialog(
    viewModel: GroupViewModel,
    groupId: String,
    foundFriends: List<User>?,
    onDismiss: () -> Unit
) {
    var userNickname by remember {
        mutableStateOf("")
    }

    var selectedUser: User? by remember {
        mutableStateOf(null)
    }

    Dialog(
        onDismissRequest = { onDismiss.invoke() },
    ) {
        Column(
            modifier = Modifier.defaultDialog(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultTopAppBar(title = stringResource(R.string.text_new_member))
            SearchBar(
                modifier = Modifier.padding(top = 15.dp),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = userNickname,
                        onQueryChange = { userNickname = it },
                        onSearch = {
                            viewModel.handleAction(GroupAction.GetUserFriends())
                        },
                        onExpandedChange = {},
                        placeholder = { Text(stringResource(R.string.text_search)) },
                        expanded = false
                    )
                },
                expanded = false,
                onExpandedChange = { }
            ) {
            }

            if (foundFriends != null) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(foundFriends) {
                        UserItem(it, it == selectedUser) {
                            selectedUser = it
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (selectedUser != null) {
                        viewModel.handleAction(
                            GroupAction.AddUserToGroup(
                                selectedUser!!.id,
                                groupId
                            )
                        )
                        onDismiss.invoke()
                    }
                },
                enabled = selectedUser != null
            ) {
                Text(stringResource(R.string.text_add))
            }
        }
    }
}