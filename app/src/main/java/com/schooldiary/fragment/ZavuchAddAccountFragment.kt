package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.schooldiary.activity.MainActivity
import com.schooldiary.databinding.FragmentZavuchAddAccountBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory


class ZavuchAddAccountFragment : Fragment() {
    private var nullableBinding: FragmentZavuchAddAccountBinding? = null
    private val binding get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nullableBinding = FragmentZavuchAddAccountBinding.inflate(inflater, container, false)


        binding.button.setOnClickListener {
            val editFio = binding.editText1.text.toString().trim()
            val editLogin = binding.editText2.text.toString().trim()
            val editPassword = binding.editText3.text.toString().trim()
            val editEmail = binding.editText4.text.toString().trim()
            val className = binding.spinner2.selectedItem.toString()
            val role = binding.spinner.selectedItem.toString()
            if (editLogin == "" || editPassword == "" ||
                editFio == "" || role == "Роль" || editEmail == "" ||
                (role == "Ученик" && className == "Класс")
            ) {
                Toast.makeText(
                    context, "Введите информацию о пользователе!", Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.createUser(editFio, editLogin, editPassword, editEmail, role, className)
            }
        }

        viewModel.dataCreatedResponse.observe(viewLifecycleOwner){
            Toast.makeText(context,it.message,Toast.LENGTH_LONG).show()
        }
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
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