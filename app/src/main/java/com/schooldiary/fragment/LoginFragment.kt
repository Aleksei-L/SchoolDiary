package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.schooldiary.R
import com.schooldiary.data.TemporaryUserStorage
import com.schooldiary.data.UserBD
import com.schooldiary.databinding.FragmentLoginBinding
import com.schooldiary.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private var nullableBinding: FragmentLoginBinding? = null
    private val binding
        get() = nullableBinding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.loginButton.setOnClickListener {
            val editLogin = binding.login.text.toString().trim()
            val editPassword = binding.password.text.toString().trim()

            if (editLogin == "" || editPassword == "")
                Toast.makeText(requireContext(), "Введите логин и пароль", Toast.LENGTH_SHORT).show()

            val currentUser = UserBD(editLogin, editPassword)
            if (TemporaryUserStorage.all_user_data.any { it.login == currentUser.login && it.password == currentUser.password }) {
                viewModel.login(binding.login.text.toString(), binding.password.text.toString())
                view.findNavController().navigate(
                    R.id.action_loginFragment_to_scheduleFragment,
                    null,
                    NavOptions.Builder().setPopUpTo(R.id.auth_flow, true).build()
                )
            } else
                Toast.makeText(context, "Логин или пароль некорректен", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
