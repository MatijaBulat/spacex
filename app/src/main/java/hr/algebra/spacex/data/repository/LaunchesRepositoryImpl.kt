package hr.algebra.spacex.data.repository

import hr.algebra.spacex.common.Resource
import hr.algebra.spacex.common.util.StoragePathFinder
import hr.algebra.spacex.common.util.downloadImageAndStore
import hr.algebra.spacex.data.local.LaunchEntity
import hr.algebra.spacex.data.local.SpaceXDatabase
import hr.algebra.spacex.data.mapper.toLaunch
import hr.algebra.spacex.data.remote.SpaceXApi
import hr.algebra.spacex.domain.model.Launch
import hr.algebra.spacex.domain.repository.LaunchesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LaunchesRepositoryImpl @Inject constructor(
    private val api: SpaceXApi,
    private val db: SpaceXDatabase
) : LaunchesRepository {
    val dao = db.dao
    val entities = mutableListOf<LaunchEntity>()
    val directoryPath = StoragePathFinder.getInternalStoragePath()

    override fun saveLaunches(): Flow<Resource<Nothing>> = flow {
        emit(Resource.Loading())
        try {
            val response = api.getLaunches()

            val launchEntities = response
                .filter { !it.links.flickr.original.isNullOrEmpty() }

            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    var counter = 0
                    launchEntities.forEach { dto ->
                        if (counter == 10) return@forEach
                        dto.links.flickr.original?.forEach {
                            val imgPath = downloadImageAndStore(
                                directoryPath!!,
                                it
                            )
                            if (imgPath != null) {
                                entities.add(
                                    LaunchEntity(
                                        _id = null,
                                        launchDate = dto.dateLocal,
                                        name = dto.name,
                                        details = dto.details,
                                        launchImageUrl = it,
                                        launchImagePath = imgPath
                                    )
                                )
                                counter++
                            }
                        }
                    }
                    dao.insertLaunches(entities)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(Resource.Error("Error!"))
        }
    }

    override suspend fun getLaunches(): List<Launch> {
        return dao.getLaunches().map { it.toLaunch() }
    }
}