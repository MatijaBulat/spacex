package hr.algebra.spacex.presentation.launches

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import hr.algebra.spacex.R
import hr.algebra.spacex.databinding.FragmentLaunchesListBinding
import hr.algebra.spacex.domain.model.Launch
import kotlinx.coroutines.flow.collectLatest


class LaunchesListFragment : Fragment() {

    private lateinit var binding: FragmentLaunchesListBinding

    private val viewModel: LaunchesViewModel by activityViewModels()

    private lateinit var launchesAdapter: LaunchesRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLaunchesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setupRecyclerView()
        viewModel.getLaunches()
    }

    private fun setObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->
                if (state.launches.isNotEmpty()) {
                    launchesAdapter.filterList(state.launches)
                }
            }
        }
    }

    private fun setupRecyclerView() {
        launchesAdapter = LaunchesRecyclerAdapter(emptyList()) { index ->
            viewModel.setSelectedLaunchIndex(index)
            //navigate
            findNavController().navigate(R.id.action_launchesListFragment_to_launchDetailsFragment)
        }
        binding.rvItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = launchesAdapter
        }
    }
}