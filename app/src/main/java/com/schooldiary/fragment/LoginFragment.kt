package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.schooldiary.databinding.FragmentLoginBinding
import com.schooldiary.R

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view =
            binding?.root ?: throw Exception("Fragment view not created yet or already destroyed")
        binding?.loginButton?.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_loginFragment_to_scheduleFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.auth_flow, true).build()
            )
        }
        return view
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}
