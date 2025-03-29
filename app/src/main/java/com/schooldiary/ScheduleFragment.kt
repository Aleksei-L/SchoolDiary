package com.schooldiary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

class ScheduleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)
        val detailsButton = view.findViewById<Button>(R.id.details_button)
        detailsButton.setOnClickListener {
            view.findNavController()
                .navigate(R.id.action_scheduleFragment_to_detailsFragment)
        }
        return view
    }
}