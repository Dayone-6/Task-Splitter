package ru.dayone.auth.data.usecase.validate_nickname

import ru.dayone.tasksplitter.common.utils.NICKNAME_MIN_LENGTH

class ValidateNicknameUseCase {
    operator fun invoke(nickname: String): NicknameValidationResult {
        if (nickname.length < NICKNAME_MIN_LENGTH) {
            return NicknameValidationResult.TooShort()
        }
        return NicknameValidationResult.Succeed()
    }
}