package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.schooldiary.databinding.FragmentScheduleBinding
import com.schooldiary.R

class ScheduleFragment : Fragment() {
    private var binding: FragmentScheduleBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)
        val view =
            binding?.root ?: throw Exception("Fragment view not created yet or already destroyed")
        binding?.detailsButton?.setOnClickListener {
            view.findNavController().navigate(R.id.action_scheduleFragment_to_detailsFragment)
        }
        return view
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
