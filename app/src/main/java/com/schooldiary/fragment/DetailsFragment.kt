package com.schooldiary.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.schooldiary.adapter.LessonAdapter
import com.schooldiary.databinding.FragmentDetailsBinding
import com.schooldiary.repository.Repository
import com.schooldiary.repository.RetrofitObject.retrofitService
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory

class DetailsFragment : Fragment() {
    private var nullableBinding: FragmentDetailsBinding? = null
    private val binding
        get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(Repository(retrofitService))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = FragmentDetailsBinding.inflate(inflater, container, false)

        val item = viewModel.scheduleData.value?.get(viewModel.dayForDetails)

        binding.detailsDayTitle.text = "${item?.weekDayName} 31.03.2025" //TODO

        binding.rvLessons.apply {
            adapter = LessonAdapter(item?.lessons)
            layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}
