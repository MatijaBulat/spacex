package hr.algebra.spacex.presentation.launches_list

import hr.algebra.spacex.domain.model.Launch

data class LaunchesState(
    val launches: List<Launch> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
