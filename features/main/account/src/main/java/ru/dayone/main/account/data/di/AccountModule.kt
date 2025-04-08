package ru.dayone.main.account.data.di

import android.content.SharedPreferences
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.dayone.main.account.data.datasource.AccountLocalDataSourceImpl
import ru.dayone.main.account.data.datasource.AccountRemoteDataSourceImpl
import ru.dayone.main.account.data.network.AccountRetrofitService
import ru.dayone.main.account.data.repository.AccountRepositoryImpl
import ru.dayone.main.account.domain.datasource.AccountLocalDataSource
import ru.dayone.main.account.domain.datasource.AccountRemoteDataSource
import ru.dayone.main.account.domain.repository.AccountRepository
import ru.dayone.main.account.presentation.account.AccountViewModel
import ru.dayone.main.account.presentation.account.state_hosting.AccountStateMachine
import ru.dayone.main.account.presentation.friends.FriendsViewModel
import ru.dayone.main.account.presentation.friends.state_hosting.FriendsStateMachine
import ru.dayone.tasksplitter.common.utils.di.network.RetrofitModule
import ru.dayone.tasksplitter.common.utils.di.network.TaskSplitterRetrofitQualifier
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsModule
import javax.inject.Singleton

@Module(includes = [SharedPrefsModule::class, RetrofitModule::class])
class AccountModule {

    @Provides
    @Singleton
    fun provideFriendsViewModel(
        stateMachine: FriendsStateMachine
    ): FriendsViewModel = FriendsViewModel(stateMachine)

    @Provides
    @Singleton
    fun provideFriendsStateMachine(
        repository: AccountRepository
    ): FriendsStateMachine = FriendsStateMachine(repository)

    @Provides
    @Singleton
    fun provideAccountViewModel(
        stateMachine: AccountStateMachine
    ): AccountViewModel = AccountViewModel(stateMachine)

    @Provides
    @Singleton
    fun provideAccountStateMachine(
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
    fun provideRemoteDataSource(
        accountService: AccountRetrofitService
    ): AccountRemoteDataSource =
        AccountRemoteDataSourceImpl(Firebase.auth, Firebase.firestore, accountService)

    @Provides
    @Singleton
    fun provideAccountRetrofitService(
        @TaskSplitterRetrofitQualifier retrofit: Retrofit
    ): AccountRetrofitService = retrofit.create(AccountRetrofitService::class.java)

}