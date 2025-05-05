package ru.dayone.main.my_groups.data.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.dayone.main.my_groups.data.datasource.GroupsLocalDataSourceImpl
import ru.dayone.main.my_groups.data.datasource.GroupsRemoteDataSourceImpl
import ru.dayone.main.my_groups.data.network.GroupsRetrofitService
import ru.dayone.main.my_groups.data.repository.GroupsRepositoryImpl
import ru.dayone.main.my_groups.domain.datasource.GroupsLocalDataSource
import ru.dayone.main.my_groups.domain.datasource.GroupsRemoteDataSource
import ru.dayone.main.my_groups.domain.repository.GroupsRepository
import ru.dayone.main.my_groups.presentation.group.GroupViewModel
import ru.dayone.main.my_groups.presentation.group.state_hosting.GroupStateMachine
import ru.dayone.main.my_groups.presentation.my_groups.MyGroupsViewModel
import ru.dayone.main.my_groups.presentation.my_groups.state_hosting.MyGroupsStateMachine
import ru.dayone.tasksplitter.common.utils.di.network.RetrofitModule
import ru.dayone.tasksplitter.common.utils.di.network.TaskSplitterRetrofitQualifier
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsModule
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class, SharedPrefsModule::class])
class GroupsModule {

    @Provides
    @Singleton
    fun provideMyGroupsViewModel(stateMachine: MyGroupsStateMachine): MyGroupsViewModel =
        MyGroupsViewModel(stateMachine)

    @Provides
    @Singleton
    fun provideMyGroupsStateMachine(
        repository: GroupsRepository
    ): MyGroupsStateMachine = MyGroupsStateMachine(repository)

    @Provides
    @Singleton
    fun provideMyGroupsRepository(
        localDataSource: GroupsLocalDataSource,
        remoteDataSource: GroupsRemoteDataSource
    ): GroupsRepository = GroupsRepositoryImpl(localDataSource, remoteDataSource)

    @Provides
    @Singleton
    fun provideLocalDataSource(
        @EncryptedSharedPrefsQualifier sharedPrefs: SharedPreferences
    ): GroupsLocalDataSource = GroupsLocalDataSourceImpl(sharedPrefs)

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        service: GroupsRetrofitService
    ): GroupsRemoteDataSource = GroupsRemoteDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideMyGroupsRetrofitService(
        @TaskSplitterRetrofitQualifier retrofit: Retrofit
    ): GroupsRetrofitService = retrofit.create(GroupsRetrofitService::class.java)


    @Provides
    @Singleton
    fun provideGroupViewModel(
        stateMachine: GroupStateMachine
    ): GroupViewModel = GroupViewModel(stateMachine)

    @Provides
    @Singleton
    fun provideGroupStateMachine(
        repository: GroupsRepository
    ): GroupStateMachine = GroupStateMachine(repository)
}