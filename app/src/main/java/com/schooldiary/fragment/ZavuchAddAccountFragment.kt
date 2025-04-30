package com.schooldiary.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.schooldiary.activity.MainActivity
import com.schooldiary.data.classname.ClassNameResponseItem
import com.schooldiary.databinding.FragmentZavuchAddAccountBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory


class ZavuchAddAccountFragment : Fragment() {
    private var nullableBinding: FragmentZavuchAddAccountBinding? = null
    private val binding get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }
    private lateinit var className: List<ClassNameResponseItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentZavuchAddAccountBinding.inflate(inflater, container, false)
        viewModel.classes.observe(viewLifecycleOwner) { subjects ->
            className = subjects
            val subjectsNames = subjects.map { it.name }.sorted()

            ArrayAdapter(
                requireContext(), R.layout.simple_spinner_item, subjectsNames
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.spinner2.adapter = adapter
            }

        }

        binding.button.setOnClickListener {
            val editFio = binding.editText1.text.toString().trim()
            val editLogin = binding.editText2.text.toString().trim()
            val editPassword = binding.editText3.text.toString().trim()
            val editEmail = binding.editText4.text.toString().trim()
            val className = binding.spinner2.selectedItem.toString()
            val role = binding.spinner.selectedItem.toString()
            if (editLogin == "" || editPassword == "" || editFio == "" || role == "Роль" || editEmail == "") {
                Toast.makeText(
                    context, "Введите информацию о пользователе!", Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.createUser(editFio, editLogin, editPassword, editEmail, role, className)
            }
        }
        viewModel.getAllClasses()
        viewModel.dataCreatedResponse.observe(viewLifecycleOwner) {
            if (it.message != "") Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                if (position == 2) {
                    binding.spinner2.visibility = View.VISIBLE
                } else {
                    binding.spinner2.visibility = View.GONE
                    binding.spinner2.setSelection(0)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                binding.spinner2.visibility = View.GONE
            }
        }
        return binding.root
    }
}