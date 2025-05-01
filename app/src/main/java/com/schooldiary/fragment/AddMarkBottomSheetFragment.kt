package com.schooldiary.fragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.schooldiary.R
import com.schooldiary.activity.MainActivity
import com.schooldiary.data.grade.CreateGradeByTeacher
import com.schooldiary.databinding.AddMarkBottomSheetBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddMarkBottomSheetFragment : BottomSheetDialogFragment() {
    private var nullableBinding: AddMarkBottomSheetBinding? = null
    private val binding get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }
    private var newMark = CreateGradeByTeacher(
        Calendar.getInstance().toInstant().toString(),
        "",
        "",
        5
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = AddMarkBottomSheetBinding.inflate(inflater, container, false)

        newMark = newMark.copy(
            studentName = viewModel.studentNameForTeacherNewMark,
            subjectName = viewModel.subjectNameForTeacherNewMark,
        )

        binding.grade5.isChecked = true

        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        binding.datePicker.text = dateFormat.format(calendar.time)

        binding.datePicker.setOnClickListener {

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            context?.let {
                val datePickerDialog =
                    DatePickerDialog(
                        it,
                        { _, selectedYear, selectedMonth, selectedDay ->
                            val selectedCalendar = Calendar.getInstance()
                            selectedCalendar.set(selectedYear, selectedMonth, selectedDay)
                            newMark = newMark.copy(data = selectedCalendar.toInstant().toString())
                            binding.datePicker.text = dateFormat.format(selectedCalendar.time)
                        },
                        year,
                        month,
                        day
                    )

                datePickerDialog.show()
            }
        }

        binding.gradeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.grade_5 -> newMark = newMark.copy(value = 5)
                R.id.grade_4 -> newMark = newMark.copy(value = 4)
                R.id.grade_3 -> newMark = newMark.copy(value = 3)
                R.id.grade_2 -> newMark = newMark.copy(value = 2)
                //TODO R.id.grade_neutral -> newMark = newMark.copy(value = null)
            }
        }

        binding.updateMark.setOnClickListener {
            viewModel.createNewGrade(newMark)
            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
