package com.schooldiary.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.schooldiary.activity.MainActivity
import com.schooldiary.databinding.FragmentEditUsersBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory


class EditUsersFragment : Fragment() {
    private var nullableBinding: FragmentEditUsersBinding? = null
    private val binding get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentEditUsersBinding.inflate(inflater, container, false)
        viewModel.userInfo.observe(viewLifecycleOwner) {
            val item = it[0]
            binding.editText1.setText(item.name)
            binding.editText2.setText(item.login)
            binding.editText3.setText(item.password)
            binding.editemail.setText(item.email)

            if (item.className != null) {
                binding.editClassName.visibility = View.VISIBLE
                loadClassesAndSelectClass(item.className)
            } else {
                binding.editClassName.visibility = View.GONE
            }
        }
        viewModel.getUserInfo(viewModel.userIdForDetails)
        viewModel.getAllClasses()
        binding.editUser.setOnClickListener {
            val editFio = binding.editText1.text.toString().trim()
            val editLogin = binding.editText2.text.toString().trim()
            val editPassword = binding.editText3.text.toString().trim()
            val editEmail = binding.editemail.text.toString().trim()
            val className =
                if (binding.editClassName.visibility == View.VISIBLE) binding.editClassName.selectedItem.toString() else null
            if (editLogin == "" || editPassword == "" || editFio == "" || editEmail == "") {
                Toast.makeText(
                    context, "Введите информацию о пользователе!", Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.updateUserInfo(
                    viewModel.userIdForDetails,
                    editFio,
                    editLogin,
                    editPassword,
                    editEmail,
                    className
                )
            }
        }
        viewModel.editDataResponse.observe(viewLifecycleOwner) {
            if (it.message != "") Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }

        return binding.root

    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }

    private fun loadClassesAndSelectClass(selectedClass: String) {
        viewModel.classes.observe(viewLifecycleOwner) { classList ->
            val className = classList.map { it.name }.sorted()
            ArrayAdapter(
                requireContext(),
                R.layout.simple_spinner_item,
                className
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.editClassName.adapter
                val position = className.indexOf(selectedClass)
                if (position != -1) {
                    binding.editClassName.setSelection(position)
                }
            }
        }
    }
}