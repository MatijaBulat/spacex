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
import hr.algebra.spacex.databinding.ActivitySplashBinding
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObserver()
        viewModel.saveLaunches()

        startAnimations()
    }

    private fun setObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->
                if (state.isLoaded) {
                    Log.v("ACTIVITYYYY", "ACTIVITYYYY")
                }
            }
        }
    }

    private fun startAnimations() {
        binding.ivSplash.applyAnimation(R.anim.rotate)
    }
}