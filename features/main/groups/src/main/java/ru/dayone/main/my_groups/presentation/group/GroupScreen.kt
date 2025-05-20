package ru.dayone.main.my_groups.presentation.group

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import ru.dayone.main.my_groups.R
import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.main.my_groups.presentation.group.state_hosting.GroupAction
import ru.dayone.main.my_groups.presentation.group.state_hosting.GroupEffect
import ru.dayone.tasksplitter.common.navigation.MyGroupsNavRoutes
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.components.DefaultTopAppBar
import ru.dayone.tasksplitter.common.utils.components.LoadingDialog
import ru.dayone.tasksplitter.common.utils.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupScreen(
    navController: NavController,
    viewModel: GroupViewModel,
    snackbarHostState: SnackbarHostState,
    group: Group
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    var isLoading by remember { mutableStateOf(false) }

    var isRefreshing by remember { mutableStateOf(false) }

    var isAddTaskDialogOpened by remember { mutableStateOf(false) }

    var isAddMemberDialogOpened by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect("effect") {
        viewModel.effect.collect {
            when (it) {
                is GroupEffect.StartLoading -> {
                    isLoading = true
                }

                is GroupEffect.StopLoading -> {
                    isLoading = false
                }

                is GroupEffect.UserAdded -> {
                    viewModel.handleAction(GroupAction.GetUsersFromGroup(group.id))
                }

                is GroupEffect.TaskCreated -> {
                    viewModel.handleAction(GroupAction.GetTasks(group.id, true))
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message = context.getString(R.string.text_task_created))
                    }
                }

                is GroupEffect.RefreshCompleted -> {
                    isRefreshing = false
                }
            }
        }
    }
    LaunchedEffect("get data") {
        viewModel.handleAction(GroupAction.GetCurrentUser())
        viewModel.handleAction(GroupAction.RefreshMembersAndTasks(group.id))
    }

    if (state.error != null) {
        LaunchedEffect(state.hashCode()) {
            isRefreshing = false
            snackbarHostState.showSnackbar(message = state.error!!.getValue(context))
            viewModel.changeState(state.copy(error = null))
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.handleAction(GroupAction.RefreshMembersAndTasks(group.id))
        },
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            modifier = if (state.tasks == null || state.tasks!!.isEmpty()) {
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState(), enabled = true)
            } else {
                Modifier.fillMaxSize()
            }
        ) {
            if (isLoading) {
                LoadingDialog()
            }
            if (isAddMemberDialogOpened) {
                AddMemberDialog(viewModel, group.id, state.friends) {
                    isAddMemberDialogOpened = false
                }
            } else if (isAddTaskDialogOpened) {
                AddTaskDialog(viewModel, group.id) {
                    isAddTaskDialogOpened = false
                }
            }
            DefaultTopAppBar(group.name, navController)
            if (state.users != null && state.tasks != null) {
                if (state.currentUser != null) {
                    Text(
                        text = stringResource(R.string.text_your_points) + ": " + group.members.find { it.memberId == state.currentUser!!.id }!!.score,
                        modifier = Modifier.padding(start = 15.dp, top = 10.dp),
                        style = titleTextStyle.copy(fontSize = 17.sp)
                    )
                }

                Text(
                    text = stringResource(R.string.text_members),
                    modifier = Modifier.padding(start = 15.dp, top = 10.dp),
                    style = titleTextStyle.copy(fontSize = 17.sp)
                )
                LazyRow(
                    modifier = Modifier.padding(10.dp)
                ) {
                    itemsIndexed(state.users!!) { index, user ->
                        UserItemSmall(user, false) {}
                        if (index == state.users!!.size - 1) {
                            UserItemSmall(user, true) {
                                isAddMemberDialogOpened = true
                            }
                        }
                    }
                }

                Text(
                    text = stringResource(R.string.text_tasks),
                    modifier = Modifier.padding(start = 15.dp, top = 10.dp),
                    style = titleTextStyle.copy(fontSize = 17.sp)
                )
                Box(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    if (!state.tasks!!.isEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(state.tasks!!) {
                                TaskItem(it) {
                                    val taskJson = GsonBuilder().create().toJson(it)
                                    navController.navigate(
                                        MyGroupsNavRoutes.TASK(
                                            taskJson,
                                            group.creatorId
                                        )
                                    )
                                }
                            }
                        }
                    } else {
                        Text(
                            text = stringResource(R.string.text_no_one_task_created_yet)
                        )
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = {
                isAddTaskDialogOpened = true
            },
            modifier = Modifier.padding(15.dp)
        ) {
            Icon(imageVector = Icons.Filled.AddTask, contentDescription = "Add task")
        }
    }
}