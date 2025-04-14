package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.schooldiary.adapter.LessonAdapter
import com.schooldiary.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var nullableBinding: FragmentDetailsBinding? = null
    private val binding
        get() = nullableBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentDetailsBinding.inflate(inflater, container, false)

        binding.detailsDayTitle.text = "Понедельник 31.03.25"

        binding.rvLessons.apply {
            adapter = LessonAdapter(listOf(Any(), Any(), Any(), Any(), Any()), context)
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
