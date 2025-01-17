package ru.dayone.auth.data.usecase.validate_password

sealed class PasswordValidationResult {
    data object Success : PasswordValidationResult()

    data object TooShort : PasswordValidationResult()
}