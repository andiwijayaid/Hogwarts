package id.andiwijaya.hogwarts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.andiwijaya.hogwarts.data.local.HogwartsDatabase
import id.andiwijaya.hogwarts.data.mediator.HogwartsRemoteMediator
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
        hogwartsRemoteMediator: HogwartsRemoteMediator
    ): HogwartsRepository = HogwartsRepositoryImpl(hogwartsDatabase, hogwartsRemoteMediator)

}