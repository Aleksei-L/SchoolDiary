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

        viewModel.scheduleData.observe(viewLifecycleOwner) {
            val scheduleAdapter = ScheduleAdapter(it)
            scheduleAdapter.setOnClickListener { position ->
                viewModel.dayForDetails = position
                findNavController().navigate(R.id.action_scheduleFragment_to_detailsFragment)
            }

            binding.rvSchedule.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = scheduleAdapter
            }
        }

        val sharedPref =
            activity?.getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE)

        sharedPref?.getString(getString(R.string.sp_class_id), "")
            ?.let { viewModel.getScheduleByClassId(it) }

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
