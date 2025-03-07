package ru.dayone.main.account.data.di

import dagger.Module
import dagger.Provides
import ru.dayone.main.account.data.datasource.FriendsLocalDataSourceImpl
import ru.dayone.main.account.data.datasource.FriendsRemoteDataSourceImpl
import ru.dayone.main.account.domain.datasource.FriendsLocalDataSource
import ru.dayone.main.account.domain.datasource.FriendsRemoteDataSource
import ru.dayone.main.account.presentation.friends.FriendsViewModel
import ru.dayone.main.account.presentation.friends.state_hosting.FriendsStateMachine
import javax.inject.Singleton

@Module
class FriendsModule {

    @Provides
    @Singleton
    fun provideViewModel(
        stateMachine: FriendsStateMachine
    ): FriendsViewModel = FriendsViewModel(stateMachine)

    @Provides
    @Singleton
    fun provideStateMachine(
        localDataSource: FriendsLocalDataSource,
        remoteDataSource: FriendsRemoteDataSource
    ): FriendsStateMachine = FriendsStateMachine(localDataSource, remoteDataSource)

    @Provides
    @Singleton
    fun provideLocalDataSource(

    ): FriendsLocalDataSource = FriendsLocalDataSourceImpl()

    @Provides
    @Singleton
    fun provideRemoteDataSource(

    ): FriendsRemoteDataSource = FriendsRemoteDataSourceImpl()
}