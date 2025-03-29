package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.schooldiary.R

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        val loginButton = view.findViewById<Button>(R.id.login_Button)
        loginButton.setOnClickListener {
            view.findNavController().navigate(
                R.id.action_loginFragment_to_scheduleFragment,
                null,
                NavOptions.Builder()
                    .setPopUpTo(R.id.auth_flow, true)
                    .build()
            )
        }
        return view
    }

}
