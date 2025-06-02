package com.example.testapp_kolsanovafit.presentation.workouts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
            binding.textViewDescription.text = workout.description
            binding.textViewDuration.text = workout.duration

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
                    setDrawableColor(binding.textViewWorkoutType, typeColor)
                }
                WorkoutType.LIVE -> {
                    binding.textViewWorkoutType.text = "Прямой эфир"
                    binding.textViewWorkoutType.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_live_workout, 0, 0 ,0)

                    val typeColor = ContextCompat.getColor(context, R.color.workoutLiveColor)

                    binding.textViewWorkoutType.setTextColor(typeColor)
                    setDurationBackground(binding.textViewDuration, typeColor)
                    setDrawableColor(binding.textViewWorkoutType, typeColor)
                }
                WorkoutType.COMPLEX -> {
                    binding.textViewWorkoutType.text = "Комплекс"
                    binding.textViewWorkoutType.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.icon_complex_workout, 0, 0 ,0)

                    val typeColor = ContextCompat.getColor(context, R.color.workoutComplexColor)

                    binding.textViewWorkoutType.setTextColor(typeColor)
                    setDurationBackground(binding.textViewDuration, typeColor)
                    setDrawableColor(binding.textViewWorkoutType, typeColor)
                }
            }
        }

        private fun setDrawableColor(textView: android.widget.TextView, colorRes: Int) {
            val context = textView.context
            val color = ContextCompat.getColor(context, colorRes)

            val drawables = textView.compoundDrawables
            drawables[0]?.let { drawable ->
                val wrappedDrawable = DrawableCompat.wrap(drawable.mutate())
                DrawableCompat.setTint(wrappedDrawable, color)
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