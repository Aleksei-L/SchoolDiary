package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.schooldiary.adapter.ReportAdapter
import com.schooldiary.databinding.FragmentStatisticsSubjectsBinding

class StatisticsSubjectsFragment : Fragment() {
    private var nullableBinding: FragmentStatisticsSubjectsBinding? = null
    private val binding
        get() = nullableBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentStatisticsSubjectsBinding.inflate(inflater, container, false)

        binding.rvReport.apply {
            adapter = ReportAdapter()
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
