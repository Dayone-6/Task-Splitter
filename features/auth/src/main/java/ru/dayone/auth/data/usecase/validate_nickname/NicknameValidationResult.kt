package ru.dayone.auth.data.usecase.validate_nickname

sealed class NicknameValidationResult {
    class Succeed : NicknameValidationResult()

    class TooShort : NicknameValidationResult()
}