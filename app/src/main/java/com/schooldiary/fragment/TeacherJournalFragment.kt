package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.schooldiary.R
import com.schooldiary.activity.MainActivity
import com.schooldiary.adapter.ReportForTeacherAdapter
import com.schooldiary.data.classname.ClassNameResponseItem
import com.schooldiary.data.subject.SubjectsResponseItem
import com.schooldiary.databinding.FragmentTeacherJournalBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory

class TeacherJournalFragment : Fragment() {
    private var nullableBinding: FragmentTeacherJournalBinding? = null
    private val binding
        get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }
    private lateinit var subjectsList: List<SubjectsResponseItem>
    private lateinit var className: List<ClassNameResponseItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentTeacherJournalBinding.inflate(inflater, container, false)
        var selectedSubject: String? = null
        var selectedClass: String? = null
        viewModel.subjects.observe(viewLifecycleOwner) { subjects ->
            subjectsList = subjects
            val subjectsNames = subjects.map { it.name }.sorted()

            ArrayAdapter(
                requireContext(), R.layout.spinner_text, subjectsNames
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.subjectsSelector.adapter = adapter
            }

            binding.subjectsSelector.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?, view: View?, position: Int, id: Long
                    ) {
                        viewModel.subjectNameForTeacherNewMark =
                            subjectsList.find { it.name == binding.subjectsSelector.selectedItem }?.name
                                ?: ""
                        viewModel.subjectIdForGrades =
                            subjectsList.find { it.name == binding.subjectsSelector.selectedItem }?.subjectId
                                ?: ""
                        selectedSubject =
                            subjectsList.find { it.name == binding.subjectsSelector.selectedItem }?.subjectId
                                ?: ""

                        if (selectedSubject != null && selectedClass != null) {
                            viewModel.getGradesForTeacher(selectedClass!!, selectedSubject!!)
                            viewModel.getAllStudents(viewModel.classNameForAdding)
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        selectedSubject = null
                    }
                }
        }
        viewModel.classes.observe(viewLifecycleOwner) { subjects ->
            className = subjects
            val subjectsNames = subjects.map { it.name }.sorted()

            ArrayAdapter(
                requireContext(), android.R.layout.simple_spinner_item, subjectsNames
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.classSelector.adapter = adapter
            }
        }

        binding.classSelector.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {

                viewModel.classNameForAdding =
                    className.find { it.name == binding.classSelector.selectedItem }?.name ?: ""
                viewModel.classIdForTeacherNewMark =
                    className.find { it.name == binding.classSelector.selectedItem }?.classId ?: ""
                selectedClass =
                    className.find { it.name == binding.classSelector.selectedItem }?.classId ?: ""
                if (selectedSubject != null && selectedClass != null) {
                    viewModel.getGradesForTeacher(selectedClass!!, selectedSubject!!)
                    viewModel.getAllStudents(viewModel.classNameForAdding)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                selectedClass = null
            }
        }

        viewModel.fragmentManagerForDatePicker = childFragmentManager

        viewModel.gradesForTeacher.observe(viewLifecycleOwner) {
            binding.rvTeacherGrades.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = ReportForTeacherAdapter(
                    it.groupBy { it.student }.toMutableMap(), childFragmentManager, viewModel
                )
            }
        }

        viewModel.getAllSubjects()
        viewModel.getAllClasses()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.allStudents.observe(viewLifecycleOwner) {
            (binding.rvTeacherGrades.adapter as? ReportForTeacherAdapter)?.addMoreStudents(it)
        }
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
