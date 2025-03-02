package ru.dayone.tasksplitter.common.utils

import android.content.Context
import androidx.annotation.StringRes

sealed class UIText {
    data class StringResource(@StringRes val id: Int) : UIText()

    data class String(val text: kotlin.String) : UIText()

    fun getValue(context: Context): kotlin.String {
        return when (this) {
            is StringResource -> context.getString(this.id)
            is String -> this.text
            else -> ""
        }
    }
}