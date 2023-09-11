package hr.algebra.spacex.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.algebra.spacex.data.repository.LaunchesRepositoryImpl
import hr.algebra.spacex.domain.repository.LaunchesRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLaunchesRepository(
        launchesRepositoryImpl: LaunchesRepositoryImpl
    ): LaunchesRepository
}
