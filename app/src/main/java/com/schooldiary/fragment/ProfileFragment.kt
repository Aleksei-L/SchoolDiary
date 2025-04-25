package com.schooldiary.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import com.schooldiary.R
import com.schooldiary.activity.MainActivity
import com.schooldiary.databinding.FragmentProfileBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory

class ProfileFragment : Fragment() {
    private var nullableBinding: FragmentProfileBinding? = null
    private val binding
        get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.exitLogout.setOnClickListener {
            logout()
        }
        return binding.root
    }

    private fun logout() {
        val sharedPref = requireContext().getSharedPreferences(
            getString(R.string.shared_pref), Context.MODE_PRIVATE
        )
        AlertDialog.Builder(requireContext()).setTitle("Внимание!")
            .setMessage("Вы уверены, что хотите выйти?").setPositiveButton("Да") { _, _ ->
                sharedPref.edit {
                    putBoolean(getString(R.string.sp_login_state), false)
                }
                viewModel.clearMessage()
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.mainFlow, true).build()
                binding.root.findNavController().navigate(
                    R.id.action_global_auth_flow, null, navOptions
                )
            }.setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
