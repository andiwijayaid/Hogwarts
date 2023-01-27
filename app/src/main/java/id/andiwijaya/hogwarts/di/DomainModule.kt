package id.andiwijaya.hogwarts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.andiwijaya.hogwarts.domain.repository.HogwartsRepository
import id.andiwijaya.hogwarts.domain.usecase.GetCharactersUseCase
import id.andiwijaya.hogwarts.domain.usecase.impl.GetCharactersUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Singleton
    @Provides
    fun provideGetCharactersUseCase(
        hogwartsRepository: HogwartsRepository
    ): GetCharactersUseCase = GetCharactersUseCaseImpl(hogwartsRepository)

}