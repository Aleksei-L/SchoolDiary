package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.schooldiary.R
import com.schooldiary.databinding.FragmentLoginBinding
import com.schooldiary.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private var binding: FragmentLoginBinding? = null
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view =
            binding?.root ?: throw Exception("Fragment view not created yet or already destroyed")
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding?.loginButton?.setOnClickListener {
            viewModel.login(binding?.login?.text.toString(), binding?.password?.text.toString())
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
