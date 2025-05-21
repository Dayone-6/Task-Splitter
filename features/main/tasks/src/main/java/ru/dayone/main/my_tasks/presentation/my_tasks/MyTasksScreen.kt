package ru.dayone.main.my_tasks.presentation.my_tasks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
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
import ru.dayone.main.my_tasks.R
import ru.dayone.main.my_tasks.presentation.my_tasks.state_hosting.MyTasksScreenAction
import ru.dayone.main.my_tasks.presentation.my_tasks.state_hosting.MyTasksScreenEffect
import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.tasksplitter.common.navigation.TasksNavRoutes
import ru.dayone.tasksplitter.common.utils.components.DefaultTopAppBar
import ru.dayone.tasksplitter.common.utils.components.LoadingDialog
import ru.dayone.tasksplitter.common.utils.components.TaskItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTasksScreen(
    navController: NavController,
    viewModel: MyTasksScreenViewModel,
    snackbarHostState: SnackbarHostState,
    isCompletedTasks: Boolean = false
) {
    val state by viewModel.state.collectAsState()

    val context = LocalContext.current

    var isLoading by remember { mutableStateOf(false) }

    var isRefreshing by remember { mutableStateOf(false) }

    val coroutineContext = rememberCoroutineScope()

    var chosenTask by remember { mutableStateOf<Task?>(null) }

    LaunchedEffect(Unit) {
        viewModel.handleAction(MyTasksScreenAction.LoadTasks(isCompletedTasks))
    }

    LaunchedEffect(
        "effect"
    ) {
        viewModel.effect.collect {
            when (it) {
                is MyTasksScreenEffect.StartLoading -> {
                    isLoading = true
                }

                is MyTasksScreenEffect.StopLoading -> {
                    isLoading = false
                }

                is MyTasksScreenEffect.TasksLoaded -> {
                    isRefreshing = false
                }

                is MyTasksScreenEffect.NavigateToTaskScreen -> {
                    navController.navigate(
                        TasksNavRoutes.TASK(
                            GsonBuilder().create().toJson(chosenTask!!), it.group.creatorId
                        )
                    )
                }
            }
        }
    }

    if (state.error != null) {
        SideEffect {
            coroutineContext.launch {
                snackbarHostState.showSnackbar(state.error!!.getValue(context))
            }
        }
    }

    if (isLoading) {
        LoadingDialog()
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.handleAction(MyTasksScreenAction.LoadTasks(isCompletedTasks))
        },
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp, start = 15.dp, end = 15.dp)
        ) {
            item {
                DefaultTopAppBar(
                    title = stringResource(
                        if (!isCompletedTasks) {
                            R.string.title_my_tasks
                        } else {
                            R.string.title_completed_task
                        }
                    ),
                    navController = if (isCompletedTasks) {
                        navController
                    } else {
                        null
                    }
                )
            }
            if (!state.tasks.isNullOrEmpty()) {
                items(state.tasks!!) {
                    TaskItem(it) {
                        chosenTask = it
                        viewModel.handleAction(MyTasksScreenAction.GetGroupById(it.groupId))
                    }
                }
            }
        }
        if (state.tasks.isNullOrEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(
                        if (!isCompletedTasks) {
                            R.string.text_there_are_no_tasks_for_you
                        } else {
                            R.string.text_you_didnt_complete_any_task
                        }
                    )
                )
            }
        }
    }
}