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
import com.schooldiary.databinding.FragmentZavuchProfileBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory

class ZavuchProfileFragment : Fragment() {
    private var nullableBinding: FragmentZavuchProfileBinding? = null
    private val binding
        get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentZavuchProfileBinding.inflate(inflater, container, false)
        binding.exitLogout.setOnClickListener {
            logout()
        }
        val sharedPref =
            activity?.getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE)
        viewModel.getUserInfo(
            sharedPref?.getString(getString(R.string.sp_user_id), "") ?: ""
        )
        binding.userRole.text =
            if (viewModel.userRole.toString() == "STUDENT") "Ученик" else if (viewModel.userRole.toString() == "TEACHER") "Учитель" else "Завуч"
        viewModel.userInfo.observe(viewLifecycleOwner) {
            binding.userName.text = it[0].name
            binding.userEmail.text = it[0].email
            if (it[0].className != null) {
                binding.className.text = it[0].className
                binding.Class.visibility = View.VISIBLE
                binding.className.visibility = View.VISIBLE
            } else {
                binding.Class.visibility = View.GONE
                binding.className.visibility = View.GONE
            }
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
                    R.id.action_global_auth_flow2, null, navOptions
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