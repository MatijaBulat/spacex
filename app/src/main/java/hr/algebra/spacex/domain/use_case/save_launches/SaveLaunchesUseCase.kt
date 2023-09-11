package hr.algebra.spacex.domain.use_case.save_launches

import hr.algebra.spacex.common.Resource
import hr.algebra.spacex.domain.repository.LaunchesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SaveLaunchesUseCase @Inject constructor(
    private val repository: LaunchesRepository
) {
    operator fun invoke(): Flow<Resource<Nothing>>  {
        return repository.saveLaunches()
    }
}