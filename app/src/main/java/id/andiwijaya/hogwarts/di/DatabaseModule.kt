package id.andiwijaya.hogwarts.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.andiwijaya.hogwarts.data.local.HogwartsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideHogwartsDatabase(
        application: Application
    ) = HogwartsDatabase.getDatabase(application)

}