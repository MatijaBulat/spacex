package hr.algebra.spacex.data.repository

import android.util.Log
import hr.algebra.spacex.common.Resource
import hr.algebra.spacex.common.util.downloadImageAndStore
import hr.algebra.spacex.data.local.LaunchEntity
import hr.algebra.spacex.data.local.SpaceXDatabase
import hr.algebra.spacex.data.mapper.toLaunch
import hr.algebra.spacex.data.mapper.toLaunchEntity
import hr.algebra.spacex.data.remote.SpaceXApi
import hr.algebra.spacex.domain.model.Launch
import hr.algebra.spacex.domain.repository.LaunchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LaunchesRepositoryImpl @Inject constructor(
    private val api: SpaceXApi,
    private val db: SpaceXDatabase
) : LaunchesRepository {
    val dao = db.dao

    override fun saveLaunches(): Flow<Resource<Nothing>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getLaunches()

            val launchEntities = response
                .filter { !it.links.flickr.original.isNullOrEmpty() }
                .take(10)
                .map { it.toLaunchEntity() }

            launchEntities.forEach{
                it.launchImagePath = downloadImageAndStore(it.launchImageUrl)
                Log.v("Ajme meni", it.launchImagePath.toString())
            }

            dao.insertLaunches(launchEntities)
            emit(Resource.Success(null) as Nothing)
        } catch (e: Exception){
            e.printStackTrace()
            emit(Resource.Error("Error!"))
        }
    }

    override suspend fun getLaunches(): List<Launch> {
        return dao.getLaunches().map { it.toLaunch() }
    }
}