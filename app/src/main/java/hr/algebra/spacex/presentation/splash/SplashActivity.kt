package hr.algebra.spacex.presentation.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import hr.algebra.spacex.R
import hr.algebra.spacex.common.util.applyAnimation
import hr.algebra.spacex.common.util.callDelayed
import hr.algebra.spacex.common.util.getBooleanPreference
import hr.algebra.spacex.common.util.isOnline
import hr.algebra.spacex.common.util.setBooleanPreference
import hr.algebra.spacex.common.util.startActivity
import hr.algebra.spacex.databinding.ActivitySplashBinding
import hr.algebra.spacex.presentation.sign_in.SignInActivity
import kotlinx.coroutines.flow.collectLatest

private const val DELAY = 3000L
const val DATA_IMPORTED = "hr.algebra.space_x.data_imported"

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObserver()
        startAnimations()
        redirect()
    }

    private fun setObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->
                if (state.isLoaded) {
                    setBooleanPreference(DATA_IMPORTED)
                    startActivity<SignInActivity>()
                    Log.v("ACTIVITYYYY", "ACTIVITYYYY")
                }
            }
        }
    }

    private fun startAnimations() {
        binding.ivSplash.applyAnimation(R.anim.rotate)
    }

    private fun redirect() {
        if (getBooleanPreference(DATA_IMPORTED)) {
            callDelayed(DELAY) { startActivity<SignInActivity>() }
        } else {
            if (isOnline()) {
                viewModel.saveLaunches()
            } else {
                callDelayed(DELAY) { finish() }
            }
        }
    }
}