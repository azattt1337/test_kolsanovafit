package com.example.testapp_kolsanovafit.presentation.player

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.testapp_kolsanovafit.R
import com.example.testapp_kolsanovafit.WorkoutApplication
import com.example.testapp_kolsanovafit.databinding.FragmentWorkoutDetailsBinding
import com.example.testapp_kolsanovafit.databinding.FragmentWorkoutListBinding
import com.example.testapp_kolsanovafit.di.ViewModelFactory
import com.example.testapp_kolsanovafit.domain.models.Workout
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class WorkoutDetailsFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: WorkoutDetailsViewModel by viewModels {
        viewModelFactory
    }

    private var _binding: FragmentWorkoutDetailsBinding? = null
    private val binding get() = _binding!!

    private var exoPlayer: ExoPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as WorkoutApplication)
            .appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args: WorkoutDetailsFragmentArgs by navArgs()
        val workout = args.workout

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)

        setupToolbar(workout)
        setupWorkoutInfo(workout)

        viewModel.loadVideoWorkout(workout.id)
        observeVideoUrl(workout.id)
    }

    private fun setupToolbar(workout: Workout) {
        binding.toolbar.title = workout.title
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupWorkoutInfo(workout: Workout) {
        binding.textViewTitle.text = workout.title

        if (workout.duration == "workout") {
            binding.textViewDuration.visibility = View.GONE
        } else {
            binding.textViewDuration.text = "Длительность: ${workout.duration} минут"
        }

        binding.textViewWorkoutType.text = "Тип: ${workout.type}"
    }

    private fun observeVideoUrl(workoutId: Int) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                when (state) {
                    is WorkoutDetailsUiState.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.contentGroup.visibility = View.GONE
                    }
                    is WorkoutDetailsUiState.Error -> {
                        binding.progressBar.isVisible = false
                        showSnackBar(state.message) {
                            viewModel.loadVideoWorkout(workoutId)
                        }
                    }
                    is WorkoutDetailsUiState.Success -> {
                        binding.progressBar.isVisible = false
                        binding.playerView.visibility = View.VISIBLE
                        setupPlayer(state.workout.videoUrl)
                    }
                }
            }
        }
    }

    private fun showSnackBar(message: String, retryAction: () -> Unit) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_INDEFINITE)
            .setAction("Повторить") { retryAction() }
            .show()
    }

    private fun setupPlayer(url: String) {
        exoPlayer = ExoPlayer.Builder(requireContext()).build().also { player ->
            binding.playerView.player = player
            val mediaItem = MediaItem.fromUri(url)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.playWhenReady = true
        }
    }

    override fun onPause() {
        super.onPause()
        exoPlayer?.pause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        exoPlayer?.release()
        exoPlayer = null
        _binding = null
    }
}