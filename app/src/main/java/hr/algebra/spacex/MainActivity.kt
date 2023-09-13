package hr.algebra.spacex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hr.algebra.spacex.presentation.save_launches.SaveLaunchesViewModel
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: SaveLaunchesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.saveLaunches()
    }
}