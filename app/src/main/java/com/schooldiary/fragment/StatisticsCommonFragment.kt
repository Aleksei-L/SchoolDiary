package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.schooldiary.databinding.FragmentStatisticsCommonBinding

class StatisticsCommonFragment : Fragment() {
    private var nullableBinding: FragmentStatisticsCommonBinding? = null
    private val binding
        get() = nullableBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentStatisticsCommonBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
