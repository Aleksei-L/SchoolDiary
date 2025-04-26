package com.schooldiary.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.schooldiary.R
import com.schooldiary.activity.MainActivity
import com.schooldiary.adapter.ReportAdapter2
import com.schooldiary.databinding.FragmentStatisticsCommonBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory

class StatisticsCommonFragment : Fragment() {
    private var nullableBinding: FragmentStatisticsCommonBinding? = null
    private val binding
        get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentStatisticsCommonBinding.inflate(inflater, container, false)

        viewModel.gradesData.observe(viewLifecycleOwner) {
            binding.rvReport2.apply {
                adapter = ReportAdapter2(it)
                layoutManager = LinearLayoutManager(context)
            }
        }

        val sharedPref =
            activity?.getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE)

        viewModel.getAllGradesForUser(
            sharedPref?.getString(getString(R.string.sp_user_id), "") ?: ""
        )
        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
