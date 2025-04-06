package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.schooldiary.R
import com.schooldiary.adapter.ScheduleAdapter
import com.schooldiary.data.ScheduleItems
import com.schooldiary.databinding.FragmentScheduleBinding

class ScheduleFragment : Fragment() {
    private var nullableBinding: FragmentScheduleBinding? = null
    private val binding
        get() = nullableBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentScheduleBinding.inflate(inflater, container, false)

        val scheduleItemsList = listOf(
            ScheduleItems("День 1"),
            ScheduleItems("День 2"),
            ScheduleItems("День 3"),
            ScheduleItems("День 4"),
            ScheduleItems("День 5"),
            ScheduleItems("День 6"),
            ScheduleItems("День 7"),
        )

        val scheduleAdapter = ScheduleAdapter(scheduleItemsList)
        scheduleAdapter.setOnClickListener {
            findNavController().navigate(R.id.action_scheduleFragment_to_detailsFragment)
        }

        binding.scheduleList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = scheduleAdapter
        }

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
