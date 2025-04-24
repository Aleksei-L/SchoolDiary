package com.schooldiary.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.schooldiary.R
import com.schooldiary.databinding.FragmentZavuchProfileBinding

class ZavuchProfileFragment : Fragment() {
    private var nullableBinding: FragmentZavuchProfileBinding? = null
    private val binding
        get() = nullableBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentZavuchProfileBinding.inflate(inflater, container, false)
        binding.exitLogout.setOnClickListener {
            logout()
        }
        return binding.root
    }

    private fun logout() {
        val sharedPref = requireContext().getSharedPreferences(
            getString(R.string.shared_pref),
            Context.MODE_PRIVATE
        )
        AlertDialog.Builder(requireContext())
            .setTitle("Внимание!")
            .setMessage("Вы уверены, что хотите выйти?")
            .setPositiveButton("Да") { _, _ ->
                sharedPref.edit {
                    putBoolean(getString(R.string.sp_login_state), false)
                }
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.mainFlow, true).build()
                binding.root.findNavController().navigate(
                    R.id.action_global_auth_flow2,
                    null,
                    navOptions
                )
            }
            .setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}