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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LaunchesRepositoryImpl @Inject constructor(
    private val api: SpaceXApi,
    private val db: SpaceXDatabase
) : LaunchesRepository {
    val dao = db.dao
    val entities = mutableListOf<LaunchEntity>()
    val directoryPath = StoragePathFinder.getInternalStoragePath()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")

    override suspend fun saveLaunches(): Resource<Nothing> {
        try {
            val response = api.getLaunches()
            val launchEntities = response
                .filter { !it.links.flickr.original.isNullOrEmpty() }

            var counter = 0
            launchEntities.forEach outer@  { dto ->
                if (counter == 10) return@outer
                dto.links.flickr.original?.forEach {
                    val imgPath = downloadImageAndStore(
                        directoryPath!!,
                        it
                    )
                    if (imgPath != null) {
                        entities.add(
                            LaunchEntity(
                                _id = null,
                                launchDate = LocalDate.parse(dto.dateLocal, formatter).toString(),
                                name = dto.name,
                                details = dto.details,
                                launchImageUrl = it,
                                launchImagePath = imgPath
                            )
                        )
                        counter++
                        return@outer
                    }
                }
            }
            dao.insertLaunches(entities)

            return Resource.Success(null)
        } catch (e: Exception) {
            e.printStackTrace()
            return (Resource.Error("Error!"))
        }
    }

    override suspend fun getLaunches(): List<Launch> {
        return dao.getLaunches().map { it.toLaunch() }
    }
}