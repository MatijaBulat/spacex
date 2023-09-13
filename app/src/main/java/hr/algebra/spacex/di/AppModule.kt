package hr.algebra.spacex.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hr.algebra.spacex.SpaceXApplication
import hr.algebra.spacex.common.Constants
import hr.algebra.spacex.data.local.SpaceXDatabase
import hr.algebra.spacex.data.remote.SpaceXApi
import hr.algebra.spacex.domain.repository.LaunchesRepository
import hr.algebra.spacex.domain.use_case.get_launches.GetLaunchesUseCase
import hr.algebra.spacex.domain.use_case.save_launches.SaveLaunchesUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSpaceXApi(): SpaceXApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpaceXApi::class.java)
    }

    @Provides
    @Singleton
    fun provideSpaceXDatabase(app: Application): SpaceXDatabase {
        return Room.databaseBuilder(
            app,
            SpaceXDatabase::class.java,
            "spaceX.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideGetLaunchesUseCase(repository: LaunchesRepository): GetLaunchesUseCase {
        return GetLaunchesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveLaunchesUseCase(repository: LaunchesRepository): SaveLaunchesUseCase {
        return SaveLaunchesUseCase(repository)
    }
}