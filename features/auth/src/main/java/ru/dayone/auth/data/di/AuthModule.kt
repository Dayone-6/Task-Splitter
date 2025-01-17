package ru.dayone.auth.data.di

import android.content.SharedPreferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import ru.dayone.auth.data.datasource.AuthLocalDataSourceImpl
import ru.dayone.auth.data.datasource.AuthRemoteFirebaseDataSourceImpl
import ru.dayone.auth.data.repository.AuthRepositoryImpl
import ru.dayone.auth.data.usecase.validate_password.ValidatePasswordUseCase
import ru.dayone.auth.domain.datasource.AuthLocalDataSource
import ru.dayone.auth.domain.datasource.AuthRemoteDataSource
import ru.dayone.auth.domain.repository.AuthRepository
import ru.dayone.auth.presentation.sign_in.AuthViewModel
import ru.dayone.auth.presentation.sign_in.state_hosting.AuthStateMachine
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsModule
import javax.inject.Singleton

@Module(includes = [SharedPrefsModule::class])
class AuthModule {
    @Singleton
    @Provides
    fun provideAuthViewModel(
        authStateMachine: AuthStateMachine
    ): AuthViewModel = AuthViewModel(authStateMachine)

    @Singleton
    @Provides
    fun provideAuthStateMachine(
        authRepository: AuthRepository,
        validatePasswordUseCase: ValidatePasswordUseCase
    ): AuthStateMachine = AuthStateMachine(authRepository, validatePasswordUseCase)

    @Singleton
    @Provides
    fun provideValidatePasswordUseCase(): ValidatePasswordUseCase = ValidatePasswordUseCase()

    @Singleton
    @Provides
    fun provideAuthRepository(
        authLocalDataSource: AuthLocalDataSource,
        authRemoteDataSource: AuthRemoteDataSource
    ): AuthRepository = AuthRepositoryImpl(authLocalDataSource, authRemoteDataSource)

    @Singleton
    @Provides
    fun provideAuthLocalDataSource(
        @EncryptedSharedPrefsQualifier prefs: SharedPreferences
    ): AuthLocalDataSource = AuthLocalDataSourceImpl(prefs)

    @Singleton
    @Provides
    fun provideAuthRemoteDataSource(): AuthRemoteDataSource = AuthRemoteFirebaseDataSourceImpl(
        Firebase.auth,
        Firebase.firestore
    )
}