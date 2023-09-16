package hr.algebra.spacex.presentation.launches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import hr.algebra.spacex.databinding.FragmentLaunchDetailsBinding
import hr.algebra.spacex.domain.model.Launch
import kotlinx.coroutines.flow.collectLatest


class LaunchDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLaunchDetailsBinding

    private val viewModel: LaunchesViewModel by activityViewModels()

    private lateinit var launches: List<Launch>

    private var selectedIndex: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLaunchDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        viewModel.getSelectedLaunchIndexAndLaunchesList()
    }

    private fun setObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->
                if (state.launches.isNotEmpty()) {
                    launches = state.launches
                }
                if (state.selectedLaunchIndex != null) {
                    selectedIndex = state.selectedLaunchIndex
                    setupViewPager()
                }
            }
        }
    }

    private fun setupViewPager() {
        binding.viewPager.adapter = LaunchesViewPagerAdapter(launches)
        binding.viewPager.currentItem = selectedIndex
    }
}