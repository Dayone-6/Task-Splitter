package ru.dayone.main.account.presentation.friends

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
import ru.dayone.main.account.R
import ru.dayone.main.account.presentation.friends.state_hosting.FriendsAction
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.theme.buttonTextStyle
import ru.dayone.tasksplitter.common.utils.components.DefaultTopAppBar
import ru.dayone.tasksplitter.common.utils.components.UserItem
import ru.dayone.tasksplitter.common.utils.defaultDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFriendDialog(
    viewModel: FriendsViewModel,
    foundUsers: List<User>?,
    nowUserId: String,
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
            DefaultTopAppBar(title = stringResource(R.string.text_search))
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = userNickname,
                        onQueryChange = { userNickname = it },
                        onSearch = {
                            viewModel.handleAction(FriendsAction.SearchUsers(userNickname))
                        },
                        onExpandedChange = { },
                        placeholder = { Text(stringResource(R.string.text_search)) },
                        expanded = false
                    )
                },
                expanded = false,
                onExpandedChange = { }
            ) {
            }

            if (foundUsers != null) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                ) {
                    items(foundUsers) {
                        if (it.id != nowUserId) {
                            UserItem(it, it == selectedUser) {
                                selectedUser = it
                            }
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (selectedUser != null) {
                        viewModel.handleAction(FriendsAction.AddFriend(selectedUser!!.id))
                    }
                },
                enabled = selectedUser != null,
                modifier = Modifier.padding(5.dp)
            ) {
                Text(stringResource(R.string.text_add), style = buttonTextStyle)
            }
        }
    }
}