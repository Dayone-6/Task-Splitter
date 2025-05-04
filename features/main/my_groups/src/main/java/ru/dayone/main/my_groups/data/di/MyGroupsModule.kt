package ru.dayone.main.my_groups.data.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.dayone.main.my_groups.data.datasource.MyGroupsLocalDataSourceImpl
import ru.dayone.main.my_groups.data.datasource.MyGroupsRemoteDataSourceImpl
import ru.dayone.main.my_groups.data.network.MyGroupsRetrofitService
import ru.dayone.main.my_groups.data.repository.MyGroupsRepositoryImpl
import ru.dayone.main.my_groups.domain.datasource.MyGroupsLocalDataSource
import ru.dayone.main.my_groups.domain.datasource.MyGroupsRemoteDataSource
import ru.dayone.main.my_groups.domain.repository.MyGroupsRepository
import ru.dayone.main.my_groups.presentation.MyGroupsViewModel
import ru.dayone.main.my_groups.presentation.state_hosting.MyGroupsStateMachine
import ru.dayone.tasksplitter.common.utils.di.network.RetrofitModule
import ru.dayone.tasksplitter.common.utils.di.network.TaskSplitterRetrofitQualifier
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsModule
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class, SharedPrefsModule::class])
class MyGroupsModule {

    @Provides
    @Singleton
    fun provideMyGroupsViewModel(stateMachine: MyGroupsStateMachine): MyGroupsViewModel =
        MyGroupsViewModel(stateMachine)

    @Provides
    @Singleton
    fun provideMyGroupsStateMachine(
        repository: MyGroupsRepository
    ): MyGroupsStateMachine = MyGroupsStateMachine(repository)

    @Provides
    @Singleton
    fun provideMyGroupsRepository(
        localDataSource: MyGroupsLocalDataSource,
        remoteDataSource: MyGroupsRemoteDataSource
    ): MyGroupsRepository = MyGroupsRepositoryImpl(localDataSource, remoteDataSource)

    @Provides
    @Singleton
    fun provideLocalDataSource(
        @EncryptedSharedPrefsQualifier sharedPrefs: SharedPreferences
    ): MyGroupsLocalDataSource = MyGroupsLocalDataSourceImpl(sharedPrefs)

    @Provides
    @Singleton
    fun provideRemoteDataSource(
        service: MyGroupsRetrofitService
    ): MyGroupsRemoteDataSource = MyGroupsRemoteDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideMyGroupsRetrofitService(
        @TaskSplitterRetrofitQualifier retrofit: Retrofit
    ): MyGroupsRetrofitService = retrofit.create(MyGroupsRetrofitService::class.java)
}