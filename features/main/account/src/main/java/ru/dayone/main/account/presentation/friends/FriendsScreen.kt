package ru.dayone.main.account.presentation.friends

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
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
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.components.DefaultTopAppBar
import ru.dayone.tasksplitter.common.utils.components.LoadingDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    navController: NavController,
    viewModel: FriendsViewModel,
    snackbarHostState: SnackbarHostState
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    var isLoading by remember {
        mutableStateOf(false)
    }

    var isAddFriendDialogShowing by remember {
        mutableStateOf(false)
    }

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
                    snackbarHostState.showSnackbar(message = "Friend Added!")
                }
            }
        }
    }

    if (state.error != null) {
        LaunchedEffect(key1 = state.hashCode()) {
            snackbarHostState.showSnackbar(message = state.error!!.getValue(context))
        }
    }

    SideEffect {
        viewModel.handleAction(FriendsAction.GetUser())
    }

    if (state.user != null) {
        SideEffect {
            viewModel.handleAction(FriendsAction.GetFriends(state.user!!.id))
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isAddFriendDialogShowing = true
                }
            ) {
                Icon(Icons.Filled.Add, "Add")
            }
        },
        floatingActionButtonPosition = FabPosition.End
    ) { innerPadding ->
        Column {
            DefaultTopAppBar(stringResource(R.string.title_friends), navController)
            if (isLoading) {
                LoadingDialog()
            }
            if (state.friends?.size == 0) {
                Text(
                    text = context.getString(R.string.text_no_one_friend_added_yet),
                    style = titleTextStyle,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                )
            } else if (state.friends != null) {
                LazyColumn(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(state.friends!!) {
                        UserItem(it) { }
                    }
                }
            }

            if (isAddFriendDialogShowing) {
                AddFriendDialog(viewModel, state.foundUsers, state.user!!.id) {
                    isAddFriendDialogShowing = false
                }
            }
        }
    }
}