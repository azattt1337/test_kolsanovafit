package com.example.testapp_kolsanovafit.presentation.player

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.testapp_kolsanovafit.R
import com.example.testapp_kolsanovafit.databinding.FragmentWorkoutDetailsBinding
import com.example.testapp_kolsanovafit.databinding.FragmentWorkoutListBinding
import com.example.testapp_kolsanovafit.di.ViewModelFactory
import javax.inject.Inject

class WorkoutDetailsFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: WorkoutDetailsViewModel by viewModels {
        viewModelFactory
    }

    private var _binding: FragmentWorkoutDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorkoutDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}