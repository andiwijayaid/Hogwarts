package id.andiwijaya.hogwarts.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.andiwijaya.hogwarts.core.Constants.Network.BASE_URL
import id.andiwijaya.hogwarts.data.local.HogwartsDatabase
import id.andiwijaya.hogwarts.data.remote.PotterDbApi
import id.andiwijaya.hogwarts.data.remote.service.HogwartsRemoteDataSource
import id.andiwijaya.hogwarts.data.repository.HogwartsRepositoryImpl
import id.andiwijaya.hogwarts.domain.repository.HogwartsRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providePotterDbApiService(): PotterDbApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PotterDbApi::class.java)

    @Singleton
    @Provides
    fun provideHogwartsDatabase(
        application: Application
    ) = HogwartsDatabase.getDatabase(application)

    @Singleton
    @Provides
    fun provideHogwartsRemoteDataSource(api: PotterDbApi) = HogwartsRemoteDataSource(api)

    @Singleton
    @Provides
    fun provideHogwartsRepository(
        hogwartsDatabase: HogwartsDatabase,
        remoteDataSource: HogwartsRemoteDataSource
    ): HogwartsRepository = HogwartsRepositoryImpl(hogwartsDatabase, remoteDataSource)
}