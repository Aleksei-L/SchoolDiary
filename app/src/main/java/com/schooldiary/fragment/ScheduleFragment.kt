package com.schooldiary.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.schooldiary.R
import com.schooldiary.activity.MainActivity
import com.schooldiary.adapter.ScheduleAdapter
import com.schooldiary.databinding.FragmentScheduleBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory
import java.time.LocalDateTime

class ScheduleFragment : Fragment() {
    private var nullableBinding: FragmentScheduleBinding? = null
    private val binding
        get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentScheduleBinding.inflate(inflater, container, false)

        binding.pastWeekButton.setOnClickListener {
            viewModel.minusWeekId()
        }

        binding.nextWeekButton.setOnClickListener {
            viewModel.plusWeekId()
        }

        val sharedPref =
            activity?.getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE)

        viewModel.weekNumber.observe(viewLifecycleOwner) {
            sharedPref?.getString(getString(R.string.sp_class_id), "")
                ?.let { viewModel.getScheduleByClassId(it) }
        }

        viewModel.scheduleData.observe(viewLifecycleOwner) {
            val item = it[0]
            val startDate = LocalDateTime.parse(item.startDate)
            val endDate = LocalDateTime.parse(item.endDate)

            val startDay =
                "${if (startDate.dayOfMonth.toString().length == 1) "0" + startDate.dayOfMonth.toString() else startDate.dayOfMonth}"
            val endDay =
                "${if (endDate.dayOfMonth.toString().length == 1) "0" + endDate.dayOfMonth.toString() else endDate.dayOfMonth}"
            val startMonth =
                "${if (startDate.monthValue.toString().length == 1) "0" + startDate.monthValue.toString() else startDate.monthValue}"
            val endMonth =
                "${if (endDate.monthValue.toString().length == 1) "0" + endDate.monthValue.toString() else endDate.monthValue}"

            binding.weekInterval.text =
                "$startDay.$startMonth.${startDate.year} - $endDay.$endMonth.${endDate.year}"

            val scheduleAdapter = ScheduleAdapter(item.schedule)
            scheduleAdapter.setOnClickListener { position ->
                viewModel.dayForDetails = position
                findNavController().navigate(R.id.action_scheduleFragment_to_detailsFragment)
            }

            binding.rvSchedule.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = scheduleAdapter
            }
        }

        sharedPref?.getString(getString(R.string.sp_class_id), "")
            ?.let { viewModel.getScheduleByClassId(it) }

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
