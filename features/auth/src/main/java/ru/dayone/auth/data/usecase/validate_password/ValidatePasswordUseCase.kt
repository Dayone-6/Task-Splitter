package ru.dayone.auth.data.usecase.validate_password

import ru.dayone.tasksplitter.common.utils.PASSWORD_MIN_LENGTH

class ValidatePasswordUseCase {
    operator fun invoke(password: String): PasswordValidationResult {
        if (password.length < PASSWORD_MIN_LENGTH) {
            return PasswordValidationResult.TooShort()
        }
        // Validation for containing letters

        // Validation for containing special symbols
        return PasswordValidationResult.Success()
    }
}