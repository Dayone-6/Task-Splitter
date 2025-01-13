package ru.dayone.taskssplitter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.dayone.login.presentation.SignInScreen
import ru.dayone.main.presentation.MainScreen
import ru.dayone.tasksplitter.common.navigation.LoginNavRoute
import ru.dayone.tasksplitter.common.navigation.MainNavRoute
import ru.dayone.tasksplitter.common.navigation.StartNavRoute
import ru.dayone.tasksplitter.common.theme.TasksSplitterTheme
import ru.dayone.tasksplitter.features.start.presentation.StartScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TasksSplitterTheme {
                if(isSystemInDarkTheme()){
                    WindowCompat.getInsetsController(window, window.decorView)
                        .isAppearanceLightStatusBars = true
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Content(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Content(
    modifier: Modifier
){
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = StartNavRoute
    ){
        composable<StartNavRoute>{
            StartScreen(
                navController
            )
        }

        composable<LoginNavRoute> {
            SignInScreen(
                navController
            )
        }

        composable<MainNavRoute> {
            MainScreen(
                navController
            )
        }
    }
}