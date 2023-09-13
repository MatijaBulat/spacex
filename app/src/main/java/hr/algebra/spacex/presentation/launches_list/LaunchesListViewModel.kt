package hr.algebra.spacex.presentation.launches_list

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.algebra.spacex.SpaceXApplication
import hr.algebra.spacex.common.Resource
import hr.algebra.spacex.domain.use_case.get_launches.GetLaunchesUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesListViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase
) : ViewModel(){

    private val _state = mutableStateOf(LaunchesState())
    val state: State<LaunchesState> = _state

    private fun getLaunches() {
        viewModelScope.launch {
            getLaunchesUseCase()
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                launches = result.data ?: emptyList(),
                            )
                        }
                        is Resource.Error -> {
                            _state.value = LaunchesState(
                                error = result.message ?: "An unexpected error occured"
                            )
                        }
                        is Resource.Loading -> {
                            _state.value = state.value.copy(
                                isLoading = true
                            )
                        }
                    }
                }
        }
    }
}