package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.schooldiary.R
import com.schooldiary.databinding.FragmentZavuchAccountslistBinding


class ZavuchAccountsListFragment : Fragment() {

    private var nullableBinding: FragmentZavuchAccountslistBinding? = null
    private val binding get() = nullableBinding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nullableBinding = FragmentZavuchAccountslistBinding.inflate(inflater, container, false)
        binding.addAccount.setOnClickListener {
            addAccount()
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
