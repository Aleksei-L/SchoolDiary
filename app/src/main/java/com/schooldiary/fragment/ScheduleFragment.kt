package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.schooldiary.databinding.FragmentScheduleBinding
import com.schooldiary.R

class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.detailsButton.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_scheduleFragment_to_detailsFragment)
        }
        return view
    }
}
