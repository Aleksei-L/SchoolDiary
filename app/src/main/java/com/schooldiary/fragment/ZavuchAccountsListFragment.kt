package com.schooldiary.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.schooldiary.R
import com.schooldiary.activity.MainActivity
import com.schooldiary.adapter.UsersAdapter
import com.schooldiary.databinding.FragmentZavuchAccountslistBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory

class ZavuchAccountsListFragment : Fragment() {

    private var nullableBinding: FragmentZavuchAccountslistBinding? = null
    private val binding get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nullableBinding = FragmentZavuchAccountslistBinding.inflate(inflater, container, false)
        binding.addAccount.setOnClickListener {
            addAccount()
        }
        viewModel.getAllUsers()

        viewModel.userData.observe(viewLifecycleOwner){

           val usersAdapter=UsersAdapter(it,parentFragmentManager)
            usersAdapter.setOnclickListener { position->
                viewModel.userForDetails=position
            }
            binding.rvAccountslist.apply {
                layoutManager=LinearLayoutManager(context)
                adapter=usersAdapter
            }
            binding.searchName.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    usersAdapter.filterByName(s.toString())
                }
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }

        return binding.root
    }

    private fun addAccount() {
        findNavController().navigate(R.id.action_zavuchAccountListFragment_to_zavuchAddAccountFragment)
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
