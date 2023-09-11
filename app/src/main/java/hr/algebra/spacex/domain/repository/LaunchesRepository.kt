package hr.algebra.spacex.domain.repository

import hr.algebra.spacex.common.Resource
import hr.algebra.spacex.domain.model.Launch
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface LaunchesRepository {
    fun saveLaunches(): Flow<Resource<Nothing>>

    suspend fun getLaunches(): List<Launch>
}