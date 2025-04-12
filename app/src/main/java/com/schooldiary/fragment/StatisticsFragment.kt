package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.schooldiary.R
import com.schooldiary.databinding.FragmentStatisticsBinding

class StatisticsFragment : Fragment() {
    private var nullableBinding: FragmentStatisticsBinding? = null
    private val binding
        get() = nullableBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentStatisticsBinding.inflate(inflater, container, false)

        childFragmentManager.beginTransaction()
            .replace(R.id.tab_content, StatisticsSubjectsFragment())
            .commit()

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment = when (tab.position) {
                    0 -> StatisticsSubjectsFragment()
                    1 -> StatisticsCommonFragment()
                    else -> Fragment()
                }
                childFragmentManager.beginTransaction()
                    .replace(R.id.tab_content, fragment)
                    .commit()
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
