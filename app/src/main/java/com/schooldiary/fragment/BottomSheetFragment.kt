package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.schooldiary.activity.MainActivity
import com.schooldiary.databinding.BottomSheetBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory

class BottomSheetFragment : BottomSheetDialogFragment() {
    private var nullableBinding: BottomSheetBinding? = null
    private val binding get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = BottomSheetBinding.inflate(inflater, container, false)

        binding.detailsHomeworkEditable.setText(viewModel.currentHomeworkForEdit)

        binding.updateHomework.setOnClickListener {
            val newHomework = binding.detailsHomeworkEditable.text.toString().trim()
            viewModel.updateHomework(newHomework)
            dismiss()
        }

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}