package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.schooldiary.databinding.FragmentScheduleBinding
import com.schooldiary.R

class ScheduleFragment : Fragment() {
    private var binding: FragmentScheduleBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleBinding.inflate(inflater, container, false)git pull origin
        val view =
            binding?.root ?: throw Exception("Fragment view not created yet or already destroyed")
        binding?.detailsButton?.setOnClickListener {
            view.findNavController().navigate(R.id.action_scheduleFragment_to_detailsFragment)
        }

        binding?.buttonExit?.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Внимание!")
                .setMessage("Вы уверены, что хотите выполнить это действие?")
                .setPositiveButton("Да") { dialog, which ->
                    view.findNavController().navigate(R.id.action_scheduleFragment_to_loginFragment)
                }
                .setNegativeButton("Нет") { dialog, which ->
                    dialog.dismiss()
                }
                .create()
                .show()

        }

        return view
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}
