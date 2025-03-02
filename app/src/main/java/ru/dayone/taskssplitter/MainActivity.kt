package ru.dayone.taskssplitter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.auth
import ru.dayone.auth.presentation.sign_in.AuthScreen
import ru.dayone.auth.presentation.sign_up.SignUpScreen
import ru.dayone.main.presentation.MainScreen
import ru.dayone.tasksplitter.common.navigation.AuthNavRoutes
import ru.dayone.tasksplitter.common.navigation.MainNavRoutes
import ru.dayone.tasksplitter.common.navigation.StartNavRoutes
import ru.dayone.tasksplitter.common.theme.TasksSplitterTheme
import ru.dayone.tasksplitter.common.utils.USER_NICKNAME_KEY
import ru.dayone.tasksplitter.features.start.presentation.StartScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()

        setContent {
            TasksSplitterTheme(
                dynamicColor = false
            ) {
                if (isSystemInDarkTheme()) {
                    WindowCompat.getInsetsController(window, window.decorView)
                        .isAppearanceLightStatusBars = true
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    val navController = rememberNavController()

    val application = LocalContext.current.applicationContext as TaskSplitterApplication

    val authComponent = remember { application.provideAuthComponent() }

    val encryptedSharedPrefs =
        remember { application.provideSharedPrefsComponent().getEncryptedSharedPrefs() }

    val mainComponent = remember { application.provideMainComponent() }

    val userNickname = remember { encryptedSharedPrefs.getString(USER_NICKNAME_KEY, null) }

    val firstNavRoute = remember {
        if (Firebase.auth.currentUser == null) {
            StartNavRoutes.Start
        } else if (userNickname == null) {
            AuthNavRoutes.SignUp
        } else {
            MainNavRoutes.Main
        }
    }

    NavHost(
        navController,
        startDestination = firstNavRoute
    ) {
        composable(StartNavRoutes.Start) {
            StartScreen(
                navController
            )
        }

        composable(AuthNavRoutes.SignIn) {
            AuthScreen(
                navController,
                authComponent.getAuthViewModel()
            )
        }

        composable(AuthNavRoutes.SignUp) {
            SignUpScreen(
                navController,
                authComponent.getSignUpViewModel()
            )
        }

        composable(MainNavRoutes.Main) {
            MainScreen(
                navController,
                mainComponent
            )
        }
    }
}