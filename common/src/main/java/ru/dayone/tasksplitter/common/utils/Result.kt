package ru.dayone.tasksplitter.common.utils

import java.lang.Exception

sealed class Result<out T> {

    data class Success<out R>(val result: R) : Result<R>()

    data class Error(val exception: Exception) : Result<Nothing>()
}