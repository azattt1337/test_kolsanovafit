package com.example.testapp_kolsanovafit.presentation.workouts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.example.testapp_kolsanovafit.R
import com.example.testapp_kolsanovafit.databinding.ItemWorkoutBinding
import com.example.testapp_kolsanovafit.domain.models.Workout
import com.example.testapp_kolsanovafit.domain.models.WorkoutType

class WorkoutsAdapter(
    private val onWorkoutClick: (Workout) -> Unit
) : ListAdapter<Workout, WorkoutsAdapter.WorkoutViewHolder>(WorkoutDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkoutsAdapter.WorkoutViewHolder {
        val binding = ItemWorkoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WorkoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WorkoutsAdapter.WorkoutViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class WorkoutViewHolder(
        private val binding: ItemWorkoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(workout: Workout) {
            binding.textViewTitle.text = workout.title

            if (workout.description == null) {
                binding.textViewDescription.visibility = View.GONE
            } else {
                binding.textViewDescription.text = workout.description
            }

            if (workout.duration != "workout") {
                binding.textViewDuration.visibility = View.VISIBLE
                binding.textViewDuration.text = "${workout.duration} минут"
            } else {
                binding.textViewDuration.visibility = View.GONE
            }

            setupWorkoutTypeStyles(workout.type)

            binding.root.setOnClickListener {
                onWorkoutClick(workout)
            }
        }

        private fun setupWorkoutTypeStyles(type: WorkoutType) {
            val context = binding.root.context

            when (type) {
                WorkoutType.TRAINING -> {
                    binding.textViewWorkoutType.text = "Тренировка"
                    binding.textViewWorkoutType.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_workout, 0, 0 ,0)

                    val typeColor = ContextCompat.getColor(context, R.color.workoutColor)

                    binding.textViewWorkoutType.setTextColor(typeColor)
                    setDurationBackground(binding.textViewDuration, typeColor)
                    setDrawableColor(binding.textViewWorkoutType, R.color.workoutColor)
                }
                WorkoutType.LIVE -> {
                    binding.textViewWorkoutType.text = "Прямой эфир"
                    binding.textViewWorkoutType.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_live_workout, 0, 0 ,0)

                    val typeColor = ContextCompat.getColor(context, R.color.workoutLiveColor)

                    binding.textViewWorkoutType.setTextColor(typeColor)
                    setDurationBackground(binding.textViewDuration, typeColor)
                    setDrawableColor(binding.textViewWorkoutType, R.color.workoutLiveColor)
                }
                WorkoutType.COMPLEX -> {
                    binding.textViewWorkoutType.text = "Комплекс"
                    binding.textViewWorkoutType.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_complex_workout, 0, 0 ,0)

                    val typeColor = ContextCompat.getColor(context, R.color.workoutComplexColor)

                    binding.textViewWorkoutType.setTextColor(typeColor)
                    setDurationBackground(binding.textViewDuration, typeColor)
                    setDrawableColor(binding.textViewWorkoutType, R.color.workoutComplexColor)
                }
            }
        }

        private fun setDrawableColor(textView: android.widget.TextView, @ColorRes colorRes: Int) {
            val context = textView.context
            val color = ContextCompat.getColor(context, colorRes)

            val drawables = textView.compoundDrawablesRelative
            val drawableStart = drawables[0].mutate()

            drawableStart?.let {
                DrawableCompat.setTint(it, color)

                textView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    it,
                    drawables[1],
                    drawables[2],
                    drawables[3])
            }
        }

        private fun setDurationBackground(textView: android.widget.TextView, color: Int) {
            val background = textView.background
            background?.let {
                val wrappedBackground = DrawableCompat.wrap(it.mutate())
                DrawableCompat.setTint(wrappedBackground, color)
            }
        }
    }

    private class WorkoutDiffCallback : DiffUtil.ItemCallback<Workout>() {
        override fun areItemsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Workout, newItem: Workout): Boolean {
            return oldItem == newItem
        }
    }
}