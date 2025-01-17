package ru.dayone.taskssplitter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import ru.dayone.auth.presentation.sign_in.AuthScreen
import ru.dayone.auth.presentation.sign_up.SignUpScreen
import ru.dayone.main.presentation.MainScreen
import ru.dayone.tasksplitter.common.navigation.AuthNavRoute
import ru.dayone.tasksplitter.common.navigation.MainNavRoute
import ru.dayone.tasksplitter.common.navigation.SignUpNavRoute
import ru.dayone.tasksplitter.common.navigation.StartNavRoute
import ru.dayone.tasksplitter.common.theme.TasksSplitterTheme
import ru.dayone.tasksplitter.features.start.presentation.StartScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()

        setContent {
            TasksSplitterTheme(
                dynamicColor = false
            ) {
                if(isSystemInDarkTheme()){
                    WindowCompat.getInsetsController(window, window.decorView)
                        .isAppearanceLightStatusBars = true
                }
                Scaffold(modifier = Modifier.fillMaxSize().imePadding()) { innerPadding ->
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

    val application = LocalContext.current.applicationContext as TaskSplitterApplication

    val authComponent = application.provideAuthComponent()

    NavHost(
        navController,
        startDestination = StartNavRoute
    ){
        composable<StartNavRoute>{
            StartScreen(
                navController
            )
        }

        composable<AuthNavRoute> {
            AuthScreen(
                navController,
                authComponent.getAuthViewModel()
            )
        }

        composable<SignUpNavRoute>{
            SignUpScreen(
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