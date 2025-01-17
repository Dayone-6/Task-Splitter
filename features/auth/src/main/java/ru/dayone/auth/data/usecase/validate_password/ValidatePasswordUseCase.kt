package ru.dayone.auth.data.usecase.validate_password

class ValidatePasswordUseCase {
    operator fun invoke(password: String) : PasswordValidationResult{
        if(password.length < 6){
            return PasswordValidationResult.TooShort
        }
        // Validation for containing letters

        // Validation for containing special symbols
        return PasswordValidationResult.Success
    }
}