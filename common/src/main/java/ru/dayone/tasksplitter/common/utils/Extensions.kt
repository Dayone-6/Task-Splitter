package ru.dayone.tasksplitter.common.utils

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import retrofit2.Call
import retrofit2.awaitResponse
import ru.dayone.tasksplitter.common.R
import ru.dayone.tasksplitter.common.exceptions.InternalServerErrorException
import ru.dayone.tasksplitter.common.exceptions.RequestCanceledException
import ru.dayone.tasksplitter.common.exceptions.UnprocessableEntityException
import ru.dayone.tasksplitter.common.models.User
import ru.dayone.tasksplitter.common.theme.currentDarkScheme
import ru.dayone.tasksplitter.common.theme.currentScheme
import ru.dayone.tasksplitter.common.theme.darkScheme


fun SharedPreferences.getUser(): User? {
    val userId = this.getString(USER_ID_KEY, null)
    val userName = this.getString(USER_NAME_KEY, "")
    val userNickname = this.getString(USER_NICKNAME_KEY, "")
    val userColor = this.getInt(USER_COLOR_KEY, 0)
    val userPoints = this.getInt(USER_POINTS_KEY, 0)
    if (userId == null) {
        return null
    }
    return User(
        userId,
        userName,
        userNickname,
        userColor,
        userPoints
    )
}

@Composable
fun Color.or(lightThemeColor: Color): Color {
    return if (isSystemInDarkTheme() && currentDarkScheme == darkScheme) {
        this
    } else {
        lightThemeColor
    }
}

fun Modifier.defaultDialog(): Modifier {
    return this
        .background(
            color = currentScheme.background,
            shape = RoundedCornerShape(20.dp)
        )
        .padding(10.dp)
}

suspend fun <T : Any> Call<T>.handle(): Result<T> {
    val result = this.awaitResponse()
//    Log.d("Network Message", result.message())
//    Log.d("Network Raw Response", result.raw().toString())
    return when (result.code()) {
        422 -> {
            Result.Error(UnprocessableEntityException(result.errorBody()!!.toString()))
        }

        500 -> {
            Result.Error(InternalServerErrorException())
        }

        200 -> {
            Result.Success(result.body()!!)
        }

        else -> {
            Result.Error(Exception())
        }
    }
}