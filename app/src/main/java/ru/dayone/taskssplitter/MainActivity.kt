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
import ru.dayone.tasksplitter.common.theme.currentDarkScheme
import ru.dayone.tasksplitter.common.theme.currentLightScheme
import ru.dayone.tasksplitter.common.utils.AUTO_THEME_CODE
import ru.dayone.tasksplitter.common.utils.DARK_THEME_CODE
import ru.dayone.tasksplitter.common.utils.DYNAMIC_THEME_CODE
import ru.dayone.tasksplitter.common.utils.LIGHT_THEME_CODE
import ru.dayone.tasksplitter.common.utils.THEME_KEY
import ru.dayone.tasksplitter.common.utils.USER_NICKNAME_KEY
import ru.dayone.tasksplitter.features.start.presentation.StartScreen

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()

        val settingsPrefs =
            (application as TaskSplitterApplication).provideSharedPrefsComponent().getSharedPrefs()
        val appThemeCode = settingsPrefs.getString(THEME_KEY, AUTO_THEME_CODE)
        val appTheme = when(appThemeCode){
            DARK_THEME_CODE -> currentDarkScheme
            LIGHT_THEME_CODE -> currentLightScheme
            else -> null
        }
        setContent {
            TasksSplitterTheme(
                dynamicColor = appThemeCode == DYNAMIC_THEME_CODE,
                forcedColorTheme = appTheme
            ) {
                if (appTheme == currentDarkScheme || (appTheme == null && isSystemInDarkTheme())) {
                    WindowCompat.getInsetsController(window, window.decorView)
                        .isAppearanceLightStatusBars = true
                }
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) {
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
            StartNavRoutes.START
        } else if (userNickname == null) {
            AuthNavRoutes.SIGN_UP
        } else {
            MainNavRoutes.MAIN
        }
    }

    NavHost(
        navController,
        startDestination = firstNavRoute
    ) {
        composable(StartNavRoutes.START) {
            StartScreen(
                navController
            )
        }

        composable(AuthNavRoutes.SIGN_IN) {
            AuthScreen(
                navController,
                authComponent.getAuthViewModel()
            )
        }

        composable(AuthNavRoutes.SIGN_UP) {
            SignUpScreen(
                navController,
                authComponent.getSignUpViewModel()
            )
        }

        composable(MainNavRoutes.MAIN) {
            MainScreen(
                navController,
                mainComponent
            )
        }
    }
}