package ru.dayone.tasksplitter.common.utils.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.dayone.tasksplitter.common.theme.titleTextStyle

@Composable
fun DefaultTopAppBar(
    title: String,
    navController: NavController? = null
){
    Box(
        modifier = Modifier.padding(top = 15.dp, start = 10.dp, end = 5.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = titleTextStyle
        )
        if(navController != null) {
            IconButton(
                modifier = Modifier.padding(5.dp),
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Arrow back"
                )
            }
        }
    }
}