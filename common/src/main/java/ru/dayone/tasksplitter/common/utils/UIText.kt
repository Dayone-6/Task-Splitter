package ru.dayone.tasksplitter.common.utils

import android.content.Context
import androidx.annotation.StringRes
import ru.dayone.tasksplitter.common.R
import ru.dayone.tasksplitter.common.exceptions.InternalServerErrorException
import ru.dayone.tasksplitter.common.exceptions.RequestCanceledException

sealed class UIText {
    data class StringResource(@StringRes val id: Int) : UIText()

    data class String(val text: kotlin.String) : UIText()

    data class Exception(val exception: kotlin.Exception) : UIText()

    fun getValue(context: Context): kotlin.String {
        return when (this) {
            is StringResource -> context.getString(this.id)
            is String -> this.text
            is UIText.Exception -> {
                when (this.exception) {
                    is InternalServerErrorException -> {
                        context.getString(R.string.error_internal_server_error)
                    }

                    is RequestCanceledException -> {
                        context.getString(R.string.error_request_canceled)
                    }

                    else -> {
                        context.getString(R.string.error_something_went_wrong)
                    }
                }
            }

            else -> ""
        }
    }
}