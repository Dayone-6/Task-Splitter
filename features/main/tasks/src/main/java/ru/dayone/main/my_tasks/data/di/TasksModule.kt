package ru.dayone.main.my_tasks.data.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import ru.dayone.main.my_tasks.data.datasource.TasksLocalDataSourceImpl
import ru.dayone.main.my_tasks.data.datasource.TasksRemoteDataSourceImpl
import ru.dayone.main.my_tasks.data.network.TasksService
import ru.dayone.main.my_tasks.data.repository.TasksRepositoryImpl
import ru.dayone.main.my_tasks.domain.datasource.TasksLocalDataSource
import ru.dayone.main.my_tasks.domain.datasource.TasksRemoteDataSource
import ru.dayone.main.my_tasks.domain.repository.TasksRepository
import ru.dayone.main.my_tasks.presentation.task.TaskViewModel
import ru.dayone.main.my_tasks.presentation.task.state_hosting.TaskStateMachine
import ru.dayone.tasksplitter.common.utils.di.network.RetrofitModule
import ru.dayone.tasksplitter.common.utils.di.network.TaskSplitterRetrofitQualifier
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.EncryptedSharedPrefsQualifier
import ru.dayone.tasksplitter.common.utils.di.shared_prefs.SharedPrefsModule
import javax.inject.Singleton

@Module(includes = [SharedPrefsModule::class, RetrofitModule::class])
class TasksModule {
    @Provides
    @Singleton
    fun provideTaskViewModel(
        stateMachine: TaskStateMachine
    ): TaskViewModel = TaskViewModel(stateMachine)

    @Provides
    @Singleton
    fun provideTasksStateMachine(
        repository: TasksRepository
    ): TaskStateMachine = TaskStateMachine(repository)

    @Provides
    @Singleton
    fun provideTasksRepository(
        localDataSource: TasksLocalDataSource,
        remoteDataSource: TasksRemoteDataSource
    ): TasksRepository = TasksRepositoryImpl(localDataSource, remoteDataSource)

    @Provides
    @Singleton
    fun provideTasksLocalDataSource(
        @EncryptedSharedPrefsQualifier sharedPrefs: SharedPreferences
    ): TasksLocalDataSource = TasksLocalDataSourceImpl(sharedPrefs)

    @Provides
    @Singleton
    fun provideTasksRemoteDataSource(
        service: TasksService
    ): TasksRemoteDataSource = TasksRemoteDataSourceImpl(service)

    @Provides
    @Singleton
    fun provideTasksService(
        @TaskSplitterRetrofitQualifier retrofit: Retrofit
    ): TasksService = retrofit.create(TasksService::class.java)
}