package ru.dayone.tasksplitter.common.exceptions

class UnprocessableEntityException(
    val errorBody: String? = null
) : Exception()