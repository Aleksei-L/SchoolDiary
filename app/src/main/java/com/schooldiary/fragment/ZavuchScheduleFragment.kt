package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.schooldiary.activity.MainActivity
import com.schooldiary.adapter.ScheduleAdapter2
import com.schooldiary.databinding.FragmentZavuchScheduleBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory

class ZavuchScheduleFragment : Fragment() {
    private var nullableBinding: FragmentZavuchScheduleBinding? = null
    private val binding
        get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        nullableBinding = FragmentZavuchScheduleBinding.inflate(inflater, container, false)
        viewModel.scheduleData.observe(viewLifecycleOwner) {
            val item = it[0]
            val scheduleAdapter = ScheduleAdapter2(item.schedule)
            binding.rvSchedule2.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = scheduleAdapter
            }

        }
        binding.mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (position != 0) {
                    val selectedItemText = parent?.getItemAtPosition(position).toString()
                    viewModel.getScheduleForZavuch(selectedItemText)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        return binding.root
    }


    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}