package ru.dayone.main.my_tasks.presentation.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import ru.dayone.main.my_tasks.R
import ru.dayone.main.my_tasks.presentation.task.state_hosting.TaskAction
import ru.dayone.main.my_tasks.presentation.task.state_hosting.TaskEffect
import ru.dayone.tasksplitter.common.models.Task
import ru.dayone.tasksplitter.common.theme.Typography
import ru.dayone.tasksplitter.common.theme.buttonTextStyle
import ru.dayone.tasksplitter.common.theme.currentScheme
import ru.dayone.tasksplitter.common.theme.successColorDark
import ru.dayone.tasksplitter.common.theme.successColorLight
import ru.dayone.tasksplitter.common.theme.titleTextStyle
import ru.dayone.tasksplitter.common.utils.components.DefaultTopAppBar
import ru.dayone.tasksplitter.common.utils.components.LoadingDialog
import ru.dayone.tasksplitter.common.utils.defaultDialog
import ru.dayone.tasksplitter.common.utils.or

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    viewModel: TaskViewModel,
    navController: NavController,
    task: Task,
    groupCreatorId: String,
    snackbarHostState: SnackbarHostState
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    var isLoading by remember { mutableStateOf(false) }

    var isRefreshing by remember { mutableStateOf(false) }

    var isVoteDialogOpened by remember { mutableStateOf(false) }

    var isConfirmationDialogOpened by remember { mutableStateOf(false) }

    var isPayOffConfirmationDialogOpened by remember { mutableStateOf(false) }

    var hasVoted by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect("load data") {
        viewModel.handleAction(TaskAction.LoadCurrentUser())
        viewModel.handleAction(TaskAction.LoadVotes(task.id))
        if (task.status > 0) {
            viewModel.handleAction(TaskAction.LoadUser(task.winner))
        }
    }

    LaunchedEffect("effect") {
        viewModel.effect.collect {
            when (it) {
                is TaskEffect.StartLoading -> {
                    isLoading = true
                }

                is TaskEffect.StopLoading -> {
                    isLoading = false
                }

                is TaskEffect.VotesLoaded -> {
                    isRefreshing = false
                }

                is TaskEffect.VoteSucceed -> {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(context.getString(R.string.text_vote_done))
                    }
                    viewModel.handleAction(TaskAction.LoadVotes(task.id, true))
                }

                is TaskEffect.EndTaskSucceed -> {
                    navController.popBackStack()
                    snackbarHostState.showSnackbar(context.getString(R.string.text_has_been_ended))
                }
            }
        }
    }

    PullToRefreshBox(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            viewModel.handleAction(TaskAction.LoadVotes(task.id))
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = if (task.status != 2) {
                Modifier.verticalScroll(rememberScrollState())
            } else {
                Modifier
            }
        ) {
            if (isLoading) {
                LoadingDialog()
            }

            if (isPayOffConfirmationDialogOpened) {
                ConfirmationDialog(
                    stringResource(R.string.text_pay_off_confirmation),
                    onDismiss = {
                        isPayOffConfirmationDialogOpened = false
                    },
                    onConfirm = {
                        isPayOffConfirmationDialogOpened = false
                        viewModel.handleAction(TaskAction.PayForTask(task.id))
                    }
                )
            }

            if (isConfirmationDialogOpened) {
                ConfirmationDialog(
                    stringResource(R.string.text_about_confirmation),
                    onConfirm = {
                        viewModel.handleAction(TaskAction.EndTask(task.id))
                        isConfirmationDialogOpened = false
                    },
                    onDismiss = {
                        isConfirmationDialogOpened = false
                    }
                )
            }

            if (isVoteDialogOpened) {
                VoteDialog(
                    onVote = {
                        viewModel.handleAction(TaskAction.Vote(task.id, it))
                        isVoteDialogOpened = false
                    },
                    onDismiss = {
                        isVoteDialogOpened = false
                    }
                )
            }

            DefaultTopAppBar(
                title = task.title,
                navController = navController
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 10.dp,
                        bottom = 10.dp,
                        start = 20.dp,
                        end = 20.dp
                    )
                    .background(
                        color = currentScheme.surfaceBright,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Text(
                    text = "${stringResource(R.string.text_status)}: " + stringResource(
                        if (task.status == 0) {
                            R.string.text_voting
                        } else if (task.status == 1 || task.status == 3) {
                            R.string.text_execution
                        } else {
                            R.string.text_completed
                        }
                    ),
                    style = Typography.bodyLarge.copy(
                        color = when (task.status) {
                            0 -> currentScheme.errorContainer
                            1 -> successColorDark.or(successColorLight)
                            3 -> successColorDark.or(successColorLight)
                            else -> Typography.bodyLarge.color
                        },
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(10.dp)
                )

                Text(
                    text = stringResource(R.string.text_description),
                    style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 15.dp, start = 10.dp)
                )
                Text(
                    text = task.description,
                    style = Typography.bodyLarge,
                    modifier = Modifier.padding(
                        top = 5.dp,
                        end = 10.dp,
                        bottom = 10.dp,
                        start = 10.dp
                    )
                )

                if (task.status > 0 && state.executor != null && state.user != null) {
                    Text(
                        text = stringResource(R.string.text_executor),
                        style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(top = 15.dp, start = 10.dp)
                    )
                    Text(
                        text = state.executor!!.name!!,
                        style = Typography.bodyLarge,
                        modifier = Modifier.padding(
                            top = 5.dp,
                            end = 10.dp,
                            bottom = 10.dp,
                            start = 10.dp
                        )
                    )
                }
            }
            if (task.status == 0 && state.votes != null && state.user != null) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.text_voted) + ": " + state.votes!!.size,
                        style = Typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                            .width(0.dp)
                    )
                    Button(
                        onClick = {
                            isVoteDialogOpened = true
                        },
                        enabled = !state.votes!!.any { it.user.id == state.user!!.id } && !hasVoted
                    ) {
                        Text(
                            text = stringResource(R.string.text_vote),
                            style = buttonTextStyle
                        )
                    }
                }
            } else if (task.status > 0 && state.executor != null && state.user != null) {
                if (task.status != 2 && state.user!!.id == groupCreatorId) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                isConfirmationDialogOpened = true
                            }
                        ) {
                            Text(
                                text = stringResource(R.string.text_confirm_completing),
                                style = buttonTextStyle
                            )
                        }
                    }
                }
            }
            if ((task.status == 1 || task.status == 3) && state.votes != null && state.user != null && task.winner == state.user!!.id) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            isPayOffConfirmationDialogOpened = true
                        },
                        enabled = task.status != 3
                    ) {
                        Text(
                            text = stringResource(R.string.text_pay_off_the_task),
                            style = buttonTextStyle
                        )
                    }
                }
            }
            if (task.status == 2 && state.votes != null) {
                Text(
                    text = stringResource(R.string.text_votes),
                    style = Typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 15.dp, start = 30.dp)
                )
                LazyColumn(
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    items(state.votes!!) {
                        VoteItem(it)
                    }
                }
            }
        }
    }
}

@Composable
fun ConfirmationDialog(
    description: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier.defaultDialog(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.text_confirmation),
                style = titleTextStyle.copy(fontSize = 20.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = description,
                style = Typography.bodyLarge,
                modifier = Modifier.padding(top = 10.dp)
            )
            Button(
                onClick = onConfirm,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.text_confirm),
                    style = buttonTextStyle
                )
            }
        }
    }
}