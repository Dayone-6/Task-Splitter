package ru.dayone.main.account.presentation.friends

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.dayone.main.account.R
import ru.dayone.main.account.presentation.friends.state_hosting.FriendsAction
import ru.dayone.main.account.presentation.friends.state_hosting.FriendsEffect
import ru.dayone.tasksplitter.common.utils.components.DefaultTopAppBar
import ru.dayone.tasksplitter.common.utils.components.LoadingDialog
import ru.dayone.tasksplitter.common.utils.components.UserItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    navController: NavController,
    viewModel: FriendsViewModel,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    var isLoading by remember {
        mutableStateOf(false)
    }

    var isAddFriendDialogShowing by remember {
        mutableStateOf(false)
    }

    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(
        "effect"
    ) {
        viewModel.effect.collect {
            when (it) {
                is FriendsEffect.StartLoading -> {
                    isLoading = true
                }

                is FriendsEffect.StopLoading -> {
                    isLoading = false
                }

                is FriendsEffect.FriendAdded -> {
                    isAddFriendDialogShowing = false
                    snackbarHostState.showSnackbar(message = context.getString(R.string.text_friend_added))
                }
            }
        }
    }

    if (state.error != null) {
        LaunchedEffect(key1 = state.hashCode()) {
            snackbarHostState.showSnackbar(message = state.error!!.getValue(context))
            viewModel.changeState(state.copy(error = null))
        }
    }

    LaunchedEffect(key1 = "Get User") {
        viewModel.handleAction(FriendsAction.GetUser())
    }

    if (state.user != null) {
        LaunchedEffect(key1 = "Get user friends") {
            viewModel.handleAction(FriendsAction.GetFriends(state.user!!.id))
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.handleAction(FriendsAction.GetFriends(state.user!!.id))
        },
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            DefaultTopAppBar(stringResource(R.string.title_friends), navController)
            if (isLoading) {
                LoadingDialog()
            }
            if (isAddFriendDialogShowing) {
                AddFriendDialog(viewModel, state.foundUsers, state.user!!.id) {
                    isAddFriendDialogShowing = false
                }
            }
            if ((state.friends ?: emptyList()).isEmpty() && !isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = context.getString(R.string.text_no_one_friend_added_yet),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            } else if ((state.friends ?: emptyList()).isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(state.friends!!) {
                        UserItem(it) { }
                    }
                }
            }
        }
        FloatingActionButton(
            onClick = {
                isAddFriendDialogShowing = true
            },
            modifier = Modifier.padding(end = 20.dp, bottom = 20.dp)
        ) {
            Icon(Icons.Filled.Add, "Add")
        }
    }
}