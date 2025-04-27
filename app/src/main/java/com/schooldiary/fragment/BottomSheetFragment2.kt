package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.schooldiary.activity.MainActivity
import com.schooldiary.databinding.BottomSheet2Binding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory

class BottomSheetFragment2 : BottomSheetDialogFragment() {
    private var nullableBinding: BottomSheet2Binding? = null
    private val binding get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val position = viewModel.userForDetails
        nullableBinding = BottomSheet2Binding.inflate(inflater, container, false)
        viewModel.userData.observe(viewLifecycleOwner) {
            val item = it[position]
            binding.nameEditable.text = item.name
            binding.roleEditable.text = item.roles[0]
            binding.emailEditable.text = item.email
            binding.passwordEditable.text = item.password
            binding.loginEditable.text = item.login
        }

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}