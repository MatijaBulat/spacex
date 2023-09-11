package hr.algebra.spacex.domain.use_case.get_launches

import hr.algebra.spacex.common.Resource
import hr.algebra.spacex.domain.model.Launch
import hr.algebra.spacex.domain.repository.LaunchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

class GetLaunchesUseCase @Inject constructor (
    private val repository: LaunchesRepository
) {
    operator fun invoke(): Flow<Resource<List<Launch>>> = flow {
        try {
            emit(Resource.Loading())
            val launches = repository.getLaunches()
            emit(Resource.Success(launches))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error!"))
        }
    }
}