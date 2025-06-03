package com.example.testapp_kolsanovafit.presentation.workouts

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.SearchEvent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp_kolsanovafit.R
import com.example.testapp_kolsanovafit.WorkoutApplication
import com.example.testapp_kolsanovafit.databinding.FragmentWorkoutListBinding
import com.example.testapp_kolsanovafit.di.ViewModelFactory
import com.example.testapp_kolsanovafit.domain.models.WorkoutType
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkoutListFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: WorkoutListViewModel by viewModels {
        viewModelFactory
    }

    private var _binding: FragmentWorkoutListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: WorkoutsAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as WorkoutApplication)
            .appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        setupMenu()
        setupRecyclerView()
        setupFilters()
        setupRetryButton()
        observeUiState()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_workout_list, menu)
                val searchItem = menu.findItem(R.id.action_search)
                val searchView = searchItem.actionView as SearchView
                searchView.queryHint = "Поиск тренировки"

                searchItem.setOnActionExpandListener(object: MenuItem.OnActionExpandListener {
                    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                        binding.toolbar.navigationIcon?.setTint(Color.WHITE)
                        return true
                    }

                    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                        return true
                    }

                })

                searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        query?.let { viewModel.searchWorkoutsByName(it) }
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewModel.searchWorkoutsByName(newText ?: "")
                        return true
                    }
                })

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun setupRecyclerView() {
        adapter = WorkoutsAdapter { workout ->

        }

        binding.recyclerViewWorkouts.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewWorkouts.adapter = adapter
    }

    private fun setupFilters() {
        binding.chipGroupFilters.setOnCheckedStateChangeListener { _, checkedId ->
            val selectedFilter = when {
                checkedId.contains(R.id.chipTraining) -> WorkoutType.TRAINING
                checkedId.contains(R.id.chipLive) -> WorkoutType.LIVE
                checkedId.contains(R.id.chipComplex) -> WorkoutType.COMPLEX
                else -> null
            }
            viewModel.filterByType(selectedFilter)
        }
    }

    private fun setupRetryButton() {
        binding.retryButton.setOnClickListener {
            viewModel.retry()
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is WorkoutListUiState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.recyclerViewWorkouts.isVisible = false
                        binding.emptyState.isVisible = false
                        binding.retryButton.isVisible = false
                    }
                    is WorkoutListUiState.Success -> {
                        binding.progressBar.isVisible = false
                        binding.recyclerViewWorkouts.isVisible = true
                        binding.emptyState.isVisible = false
                        adapter.submitList(state.workouts)
                    }
                    is WorkoutListUiState.Error -> {
                        binding.progressBar.isVisible = false
                        binding.recyclerViewWorkouts.isVisible = false
                        binding.emptyState.isVisible = true
                        binding.emptyMessage.text = state.message
                        binding.retryButton.isVisible = true
                    }
                    is WorkoutListUiState.Empty -> {
                        binding.progressBar.isVisible = false
                        binding.recyclerViewWorkouts.isVisible = false
                        binding.emptyState.isVisible = true
                        binding.emptyMessage.text = state.message
                        binding.retryButton.isVisible = true
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}