package ru.dayone.main.account.data.di

import dagger.Module
import dagger.Provides
import ru.dayone.main.account.data.datasource.UserDataLocalDataSourceImpl
import ru.dayone.main.account.data.datasource.UserDataRemoteDataSourceImpl
import ru.dayone.main.account.domain.datasource.UserDataLocalDataSource
import ru.dayone.main.account.domain.datasource.UserDataRemoteDataSource
import ru.dayone.main.account.presentation.completed_tasks.CompletedTasksViewModel
import ru.dayone.main.account.presentation.completed_tasks.state_hosting.CompletedTasksStateMachine
import ru.dayone.main.account.presentation.friends.FriendsViewModel
import ru.dayone.main.account.presentation.friends.state_hosting.FriendsStateMachine
import javax.inject.Singleton

@Module
class UserDataModule {

    @Provides
    @Singleton
    fun provideCompletedTasksViewModel(
        stateMachine: CompletedTasksStateMachine
    ): CompletedTasksViewModel = CompletedTasksViewModel(stateMachine)

    @Provides
    @Singleton
    fun provideCompletedTasksStateMachine(
        localDataSource: UserDataLocalDataSource,
        remoteDataSource: UserDataRemoteDataSource
    ): CompletedTasksStateMachine = CompletedTasksStateMachine(localDataSource, remoteDataSource)

    @Provides
    @Singleton
    fun provideFriendsViewModel(
        stateMachine: FriendsStateMachine
    ): FriendsViewModel = FriendsViewModel(stateMachine)

    @Provides
    @Singleton
    fun provideFriendsStateMachine(
        localDataSource: UserDataLocalDataSource,
        remoteDataSource: UserDataRemoteDataSource
    ): FriendsStateMachine = FriendsStateMachine(localDataSource, remoteDataSource)

    @Provides
    @Singleton
    fun provideLocalDataSource(

    ): UserDataLocalDataSource = UserDataLocalDataSourceImpl()

    @Provides
    @Singleton
    fun provideRemoteDataSource(

    ): UserDataRemoteDataSource = UserDataRemoteDataSourceImpl()
}