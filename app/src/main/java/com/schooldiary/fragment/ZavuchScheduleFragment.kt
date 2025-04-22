package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.schooldiary.R
import com.schooldiary.databinding.FragmentZavuchScheduleBinding

class ZavuchScheduleFragment : Fragment() {
    private var nullableBinding: FragmentZavuchScheduleBinding? = null
    private val binding
        get() = nullableBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nullableBinding = FragmentZavuchScheduleBinding.inflate(inflater, container, false)
        binding.toDetails.setOnClickListener {
            findNavController().navigate(R.id.action_zavuchScheduleFragment_to_zavuchDetailsFragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}