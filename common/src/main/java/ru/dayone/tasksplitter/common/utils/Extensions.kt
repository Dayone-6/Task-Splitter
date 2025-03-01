package ru.dayone.tasksplitter.common.utils

import android.content.SharedPreferences
import ru.dayone.tasksplitter.common.models.User


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