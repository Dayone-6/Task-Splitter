package ru.dayone.tasksplitter.common.utils

import android.content.SharedPreferences
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.theme.currentDarkScheme
import ru.dayone.tasksplitter.common.theme.currentLightScheme
import ru.dayone.tasksplitter.common.theme.darkScheme
import ru.dayone.tasksplitter.common.theme.lightScheme


fun SharedPreferences.getUser(): User? {
    val userId = this.getString(USER_ID_KEY, null)
    val userName = this.getString(USER_NAME_KEY, "")
    val userNickname = this.getString(USER_NICKNAME_KEY, "")
    val userColor = this.getInt(USER_COLOR_KEY, 0)
    if (userId == null) {
        return null
    }
    return User(
        userId,
        userName,
        userNickname,
        userColor
    )
}

@Composable
fun Color.or(lightThemeColor: Color): Color{
    return if(isSystemInDarkTheme() && currentDarkScheme == darkScheme){
        this
    }else{
        lightThemeColor
    }
}