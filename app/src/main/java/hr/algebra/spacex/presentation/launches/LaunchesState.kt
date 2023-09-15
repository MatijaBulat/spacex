package hr.algebra.spacex.presentation.launches

import hr.algebra.spacex.domain.model.Launch

data class LaunchesState(
    val selectedLaunchIndex: Int? = null,
    val launches: List<Launch> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
