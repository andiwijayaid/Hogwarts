package id.andiwijaya.hogwarts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.andiwijaya.hogwarts.data.local.HogwartsDatabase
import id.andiwijaya.hogwarts.data.remote.service.HogwartsRemoteDataSource
import id.andiwijaya.hogwarts.data.repository.HogwartsRepositoryImpl
import id.andiwijaya.hogwarts.domain.repository.HogwartsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideHogwartsRepository(
        hogwartsDatabase: HogwartsDatabase,
        remoteDataSource: HogwartsRemoteDataSource
    ): HogwartsRepository = HogwartsRepositoryImpl(hogwartsDatabase, remoteDataSource)

}