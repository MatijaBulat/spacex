package hr.algebra.spacex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import hr.algebra.spacex.databinding.ActivityMainBinding
import hr.algebra.spacex.presentation.launches.LaunchesViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: LaunchesViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}