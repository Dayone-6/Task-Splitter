package ru.dayone.auth.data.usecase.validate_password

sealed class PasswordValidationResult {
    class Success : PasswordValidationResult()

    class TooShort : PasswordValidationResult()
}