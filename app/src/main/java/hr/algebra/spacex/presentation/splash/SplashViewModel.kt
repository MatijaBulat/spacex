package hr.algebra.spacex.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hr.algebra.spacex.common.Resource
import hr.algebra.spacex.domain.use_case.save_launches.SaveLaunchesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val saveLaunchesUseCase: SaveLaunchesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SplashState())
    val state: StateFlow<SplashState> = _state

    fun saveLaunches() {
        viewModelScope.launch(Dispatchers.IO) {
            val isSuccess = saveLaunchesUseCase()
            if (isSuccess is Resource.Success) {
                Log.v("SUCCESS VIEWMODELL", "MODELL")
                withContext(Dispatchers.Main) {
                    _state.value = _state.value.copy(isLoaded = true)
                }
            }
        }
    }
}