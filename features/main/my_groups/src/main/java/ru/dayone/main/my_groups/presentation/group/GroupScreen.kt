package ru.dayone.main.my_groups.presentation.group

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import ru.dayone.main.my_groups.R
import ru.dayone.main.my_groups.data.network.models.Group
import ru.dayone.main.my_groups.presentation.group.state_hosting.GroupEffect
import ru.dayone.tasksplitter.common.utils.components.DefaultTopAppBar

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
                    snackbarHostState.showSnackbar(message = context.getString(R.string.text_member_added))
                }
            }
        }
    }
    Column {
        DefaultTopAppBar(title = group.name, navController)

    }
}