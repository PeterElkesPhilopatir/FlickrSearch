package com.peter.flickrsearch.di


import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.peter.flickrsearch.Constants.BASE_URL
import com.peter.flickrsearch.Constants.DATABASE_NAME
import com.peter.flickrsearch.business.repositories.PhotosRepo
import com.peter.flickrsearch.business.repositories.PhotosRepoImpl
import com.peter.flickrsearch.business.usecases.PhotosUseCase
import com.peter.flickrsearch.business.usecases.PhotosUseCaseImpl
import com.peter.flickrsearch.framework.database.PhotoDao
import com.peter.flickrsearch.framework.database.PhotosDatabase
import com.peter.flickrsearch.framework.datasource.ApiService
import com.peter.flickrsearch.framework.datasource.SearchSource
import com.peter.flickrsearch.framework.datasource.SearchSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhotoSearchProviderModule {

    @Singleton
    @Provides
    fun providerSearchSource(
        api: ApiService,
        dao: PhotoDao
    ): SearchSource =
        SearchSourceImpl(api, dao)

    @Singleton
    @Provides
    fun providerPhotosRepo(searchSource: SearchSource): PhotosRepo =
        PhotosRepoImpl(searchSource)

    @Singleton
    @Provides
    fun providerPhotosUseCase(PhotosRepo: PhotosRepo): PhotosUseCase =
        PhotosUseCaseImpl(PhotosRepo)

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext app: Context
    ): PhotosDatabase = Room.databaseBuilder(
        app,
        PhotosDatabase::class.java,
        DATABASE_NAME
    )
        .allowMainThreadQueries().build()

    @Singleton
    @Provides
    fun provideDao(db: PhotosDatabase): PhotoDao = db.photoDao()

}