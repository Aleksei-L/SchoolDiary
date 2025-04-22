package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.schooldiary.activity.MainActivity
import com.schooldiary.adapter.LessonAdapter
import com.schooldiary.databinding.FragmentDetailsBinding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory
import java.time.LocalDateTime

class DetailsFragment : Fragment() {
    private var nullableBinding: FragmentDetailsBinding? = null
    private val binding
        get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentDetailsBinding.inflate(inflater, container, false)

        val item = viewModel.scheduleData.value?.get(0)

        val date = LocalDateTime.parse(item?.startDate).plusDays(viewModel.dayForDetails.toLong())
        val day =
            "${if (date.dayOfMonth.toString().length == 1) "0" + date.dayOfMonth.toString() else date.dayOfMonth}"
        val month =
            "${if (date.monthValue.toString().length == 1) "0" + date.monthValue.toString() else date.monthValue}"
        val year = date.year

        binding.detailsDayTitle.text =
            "${item?.schedule?.get(viewModel.dayForDetails)?.weekDayName} $day.$month.$year"

        binding.rvLessons.apply {
            adapter = LessonAdapter(item?.schedule?.get(viewModel.dayForDetails)?.lessons)
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
