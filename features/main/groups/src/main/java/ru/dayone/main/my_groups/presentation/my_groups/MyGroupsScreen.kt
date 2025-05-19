package ru.dayone.main.my_groups.presentation.my_groups

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import ru.dayone.main.my_groups.R
import ru.dayone.main.my_groups.presentation.my_groups.state_hosting.MyGroupsAction
import ru.dayone.main.my_groups.presentation.my_groups.state_hosting.MyGroupsEffect
import ru.dayone.tasksplitter.common.navigation.MyGroupsNavRoutes
import ru.dayone.tasksplitter.common.utils.components.DefaultTopAppBar
import ru.dayone.tasksplitter.common.utils.components.LoadingDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyGroupsScreen(
    navController: NavController,
    viewModel: MyGroupsViewModel,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    var isLoading by remember { mutableStateOf(false) }
    var isCreateGroupDialogShowing by remember { mutableStateOf(false) }
    var isRefreshing by remember { mutableStateOf(false) }
    var pullToRefreshState = rememberPullToRefreshState()

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect("effect") {
        viewModel.effect.collect {
            when (it) {
                is MyGroupsEffect.StartLoading -> {
                    isLoading = true
                }

                is MyGroupsEffect.StopLoading -> {
                    isLoading = false
                }

                is MyGroupsEffect.GroupCreated -> {
                    viewModel.handleAction(MyGroupsAction.GetGroups(true))
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message = context.getString(R.string.text_group_created))
                    }
                }

                is MyGroupsEffect.RequestedGroupsLoaded -> {
                    isRefreshing = false
                }
            }
        }
    }

    LaunchedEffect("get groups") {
        viewModel.handleAction(MyGroupsAction.GetGroups())
    }

    if (state.error != null) {
        SideEffect {
            val error = state.error!!
            viewModel.changeState(state.copy(error = null))
            isRefreshing = false
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message = error.getValue(context))
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        state = pullToRefreshState,
        onRefresh = {
            isRefreshing = true
            viewModel.handleAction(MyGroupsAction.GetGroups(requireNew = true))
        },
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = if (state.groups == null || state.groups!!.isEmpty()) {
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            } else {
                Modifier.fillMaxSize()
            },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DefaultTopAppBar(title = stringResource(R.string.title_my_groups))
            if (isLoading) {
                LoadingDialog()
            } else if (state.groups != null && state.groups!!.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(state.groups!!) {
                        GroupItem(it) {
                            navController.navigate(
                                MyGroupsNavRoutes.GROUP(
                                    GsonBuilder().create().toJson(it)
                                )
                            )
                        }
                    }
                }
            }
        }

        if (isCreateGroupDialogShowing) {
            CreateGroupDialog(
                viewModel,
                onDismiss = {
                    isCreateGroupDialogShowing = false
                }
            )
        }
        if ((state.groups ?: emptyList()).isEmpty()) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(text = stringResource(R.string.text_there_are_no_groups_you_consist_in))
            }
        }
        FloatingActionButton(
            onClick = { isCreateGroupDialogShowing = true },
            modifier = Modifier.padding(end = 20.dp, bottom = 20.dp)
        ) {
            Icon(imageVector = Icons.Filled.GroupAdd, contentDescription = "Add group")
        }
    }

}