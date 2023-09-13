package hr.algebra.spacex.presentation.save_launches

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.algebra.spacex.common.Resource
import hr.algebra.spacex.domain.use_case.save_launches.SaveLaunchesUseCase
import hr.algebra.spacex.presentation.launches_list.LaunchesState
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveLaunchesViewModel @Inject constructor(
    private val saveLaunchesUseCase: SaveLaunchesUseCase
) : ViewModel() {


    private val _state = mutableStateOf(LaunchesState())
    val state: State<LaunchesState> = _state

    /*   private fun redirect() {

        if (getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) { startActivity<HostActivity>() }

        } else {
            if (isOnline()) {
                NasaService.enqueue(this)
            } else {
                binding.tvSplash.text = getString(R.string.no_internet)
                callDelayed(DELAY) { finish() }
            }
        }
    }*/
   /* init {
        saveLaunches()
    }*/

    fun saveLaunches() {
        viewModelScope.launch {
            saveLaunchesUseCase()
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            Log.v("ViewModel Ajme", "Meni");
                        }
                        is Resource.Error -> {

                        }
                        is Resource.Loading -> {

                        }
                    }
                }
        }
    }

}