package ru.dayone.main.account.data.di

import android.content.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import ru.dayone.main.account.data.datasource.AccountLocalDataSourceImpl
import ru.dayone.main.account.data.datasource.AccountRemoteDataSourceImpl
import ru.dayone.main.account.data.repository.AccountRepositoryImpl
import ru.dayone.main.account.domain.datasource.AccountLocalDataSource
import ru.dayone.main.account.domain.datasource.AccountRemoteDataSource
import ru.dayone.main.account.domain.repository.AccountRepository
import ru.dayone.main.account.presentation.AccountViewModel
import ru.dayone.main.account.presentation.state_hosting.AccountStateMachine
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsModule
import javax.inject.Singleton

@Module(includes = [SharedPrefsModule::class])
class AccountModule {

    @Provides
    @Singleton
    fun provideViewModel(
        stateMachine: AccountStateMachine
    ): AccountViewModel = AccountViewModel(stateMachine)

    @Provides
    @Singleton
    fun provideStateMachine(
        repository: AccountRepository
    ): AccountStateMachine = AccountStateMachine(repository)

    @Provides
    @Singleton
    fun provideRepository(
        localDataSource: AccountLocalDataSource,
        remoteDataSource: AccountRemoteDataSource
    ): AccountRepository = AccountRepositoryImpl(localDataSource, remoteDataSource)

    @Provides
    @Singleton
    fun provideLocalDataSource(
        @EncryptedSharedPrefsQualifier sharedPrefs: SharedPreferences
    ): AccountLocalDataSource = AccountLocalDataSourceImpl(sharedPrefs)

    @Provides
    @Singleton
    fun provideRemoteDataSource(): AccountRemoteDataSource =
        AccountRemoteDataSourceImpl(Firebase.auth)
}