package com.schooldiary.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.schooldiary.R
import com.schooldiary.activity.MainActivity
import com.schooldiary.databinding.FragmentLoginBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory

class LoginFragment : Fragment() {
    private var nullableBinding: FragmentLoginBinding? = null
    private val binding
        get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.loginButton.setOnClickListener {
            val editLogin = binding.login.text.toString().trim()
            val editPassword = binding.password.text.toString().trim()

            if (editLogin == "" || editPassword == "")
                Toast.makeText(context, "Введите логин и пароль", Toast.LENGTH_SHORT).show()
            else
                viewModel.login(editLogin, editPassword)
        }

        val sharedPref =
            activity?.getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE)

        viewModel.loginData.observe(viewLifecycleOwner) {
            if (it.message == "Пользователь успешно аутентифицирован") {
                sharedPref?.edit {
                    putBoolean(getString(R.string.sp_login_state), true)
                    putString(getString(R.string.sp_class_id), it.classId)
                    putString(getString(R.string.sp_user_id), it.userId)
                }
                loginUser()
            } else {
                Toast.makeText(context, "Логин или пароль некорректен", Toast.LENGTH_SHORT).show()
            }
        }

        if (sharedPref != null && sharedPref.getBoolean(getString(R.string.sp_login_state), false))
            loginUser()

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }

    private fun loginUser() {
        findNavController().navigate(
            R.id.action_loginFragment_to_scheduleFragment,
            null,
            NavOptions.Builder().setPopUpTo(R.id.auth_flow, true).build()
        )
    }
}
