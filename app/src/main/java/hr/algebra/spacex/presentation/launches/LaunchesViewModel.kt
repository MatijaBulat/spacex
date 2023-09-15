package hr.algebra.spacex.presentation.launches

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.algebra.spacex.common.Resource
import hr.algebra.spacex.domain.model.Launch
import hr.algebra.spacex.domain.use_case.get_launches.GetLaunchesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchesViewModel @Inject constructor(
    private val getLaunchesUseCase: GetLaunchesUseCase
) : ViewModel() {

    private var launches = listOf<Launch>()

    private var selectedLaunchIndex: Int = 0

    private val _state = MutableStateFlow(LaunchesState())
    val state: StateFlow<LaunchesState> = _state

    fun getSelectedLaunchIndexAndLaunchesList() {
        _state.value =
            _state.value.copy(selectedLaunchIndex = selectedLaunchIndex, launches = launches)
    }

    fun setSelectedLaunchIndex(launchId: Int) {
        selectedLaunchIndex = launchId
    }


    fun getLaunches() {
        viewModelScope.launch {
            getLaunchesUseCase()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = state.value.copy(
                                launches = result.data ?: emptyList(),
                            )
                            launches = result.data ?: emptyList()
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