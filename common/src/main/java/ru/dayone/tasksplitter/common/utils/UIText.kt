package ru.dayone.tasksplitter.common.utils

import androidx.annotation.StringRes

sealed class UIText {
    data class StringResource(@StringRes val id: Int) : UIText()

    data class String(val text: kotlin.String) : UIText()
}