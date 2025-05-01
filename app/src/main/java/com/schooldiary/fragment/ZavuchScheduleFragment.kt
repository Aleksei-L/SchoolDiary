package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.schooldiary.activity.MainActivity
import com.schooldiary.adapter.ScheduleAdapter2
import com.schooldiary.data.classname.ClassNameResponseItem
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
    private lateinit var className: List<ClassNameResponseItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentZavuchScheduleBinding.inflate(inflater, container, false)
        viewModel.classes.observe(viewLifecycleOwner) { subjects ->
            className = subjects
            val subjectsNames = subjects.map { it.name }.sorted()

            ArrayAdapter(
                requireContext(), android.R.layout.simple_spinner_item, subjectsNames
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.mySpinner.adapter = adapter
            }
            binding.mySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    viewModel.getScheduleForZavuch(
                        className.find { it.name == binding.mySpinner.selectedItem }?.name ?: ""
                    )
                    viewModel.classNameForAdding =
                        className.find { it.name == binding.mySpinner.selectedItem }?.name ?: ""
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        }


        viewModel.scheduleData.observe(viewLifecycleOwner) {
            val item = it[0]
            val scheduleAdapter = ScheduleAdapter2(item.schedule, parentFragmentManager,requireContext())
            scheduleAdapter.setOnClickListener { position ->
                viewModel.dayForDetails = position+1}
            binding.rvSchedule2.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = scheduleAdapter
            }
        }
        viewModel.addLessonsResponse.observe(viewLifecycleOwner) {
            if (it.message != "") Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
        viewModel.getAllClasses()
        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}