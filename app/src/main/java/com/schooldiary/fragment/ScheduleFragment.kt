package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.schooldiary.R
import com.schooldiary.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {
    private var nullableBinding: FragmentScheduleBinding? = null
    private val binding
        get() = nullableBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentScheduleBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.detailsButton.setOnClickListener {
            view.findNavController().navigate(R.id.action_scheduleFragment_to_detailsFragment)
        }

        return view
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
