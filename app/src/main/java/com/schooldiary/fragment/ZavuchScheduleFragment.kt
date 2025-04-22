package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.schooldiary.R
import com.schooldiary.databinding.FragmentZavuchScheduleBinding

class ZavuchScheduleFragment : Fragment() {
    private var nullableBinding: FragmentZavuchScheduleBinding? = null
    private val binding
        get() = nullableBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        nullableBinding = FragmentZavuchScheduleBinding.inflate(inflater, container, false)
        binding.toDetails.setOnClickListener {
            findNavController().navigate(R.id.action_zavuchScheduleFragment_to_zavuchDetailsFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val spinner = binding.mySpinner

        ArrayAdapter.createFromResource(requireContext(), R.array.my_items, android.R.layout.simple_spinner_item)
            .also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "Выбрано: $selectedItem", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.toDetails.setOnClickListener {
            findNavController().navigate(R.id.action_zavuchScheduleFragment_to_zavuchDetailsFragment)
        }
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}