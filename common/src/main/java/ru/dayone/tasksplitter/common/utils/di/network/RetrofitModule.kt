package ru.dayone.tasksplitter.common.utils.di.network

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dayone.tasksplitter.common.utils.BACKEND_BASE_URL
import javax.inject.Singleton

@Module
class RetrofitModule {

    @Singleton
    @TaskSplitterRetrofitQualifier
    @Provides
    fun provideTaskSplitterRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BACKEND_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}