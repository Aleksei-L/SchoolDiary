package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.schooldiary.R
import com.schooldiary.databinding.FragmentLoginBinding
import com.schooldiary.repository.Repository
import com.schooldiary.repository.RetrofitObject.retrofitService
import com.schooldiary.viewmodel.LoginViewModel
import com.schooldiary.viewmodel.LoginViewModelFactory

class LoginFragment : Fragment() {
    private var nullableBinding: FragmentLoginBinding? = null
    private val binding
        get() = nullableBinding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentLoginBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(Repository(retrofitService))
        )[LoginViewModel::class.java]

        binding.loginButton.setOnClickListener {
            val editLogin = binding.login.text.toString().trim()
            val editPassword = binding.password.text.toString().trim()

            if (editLogin == "" || editPassword == "")
                Toast.makeText(context, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
            else
                viewModel.login(editLogin, editPassword)
        }

        viewModel.loginStatus.observe(viewLifecycleOwner) { hasLoginSuccess ->
            if (hasLoginSuccess) {
                findNavController().navigate(
                    R.id.action_loginFragment_to_scheduleFragment,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.auth_flow, true).build()
                )
            } else {
                Toast.makeText(context, "Логин или пароль некорректен", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
