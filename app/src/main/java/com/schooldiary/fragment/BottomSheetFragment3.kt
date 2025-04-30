package com.schooldiary.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.schooldiary.activity.MainActivity
import com.schooldiary.data.room.RoomResponseItem
import com.schooldiary.data.subject.SubjectsResponseItem
import com.schooldiary.data.users.User
import com.schooldiary.databinding.BottomSheet3Binding
import com.schooldiary.viewmodel.MainViewModel
import com.schooldiary.viewmodel.MainViewModelFactory

class BottomSheetFragment3 : BottomSheetDialogFragment() {
    private var nullableBinding: BottomSheet3Binding? = null
    private val binding get() = nullableBinding!!
    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory((activity as MainActivity).repository)
    }
    private lateinit var subjectsList: List<SubjectsResponseItem>
    private lateinit var roomList:List<RoomResponseItem>
    private lateinit var teacherList:List<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        nullableBinding = BottomSheet3Binding.inflate(inflater, container, false)

        viewModel.subjects.observe(viewLifecycleOwner) { subjects ->
            subjectsList = subjects
            val subjectsNames = subjects.map { it.name }.sorted()

            ArrayAdapter(
                requireContext(),
                R.layout.simple_spinner_item,
                subjectsNames
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.lessonsName.adapter = adapter
            }
        }
        viewModel.room.observe(viewLifecycleOwner) { room ->
            roomList = room
            val rooms = room.map { it.room }.sorted()

            ArrayAdapter(
                requireContext(),
                R.layout.simple_spinner_item,
                rooms
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.rooms.adapter = adapter
            }
        }
        viewModel.userData.observe(viewLifecycleOwner) { teacher ->
            teacherList = teacher
            val subjectsNames = teacher
                .filter { it.roles[0]=="Учитель" }
                .map { it.name }.sorted()

            ArrayAdapter(
                requireContext(),
                R.layout.simple_spinner_item,
                subjectsNames
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
                binding.teachersName.adapter = adapter
            }
        }
        viewModel.getAllUsers()
        viewModel.getAllSubjects()
        viewModel.getAllRooms()

        return binding.root
    }

    override fun onDestroyView() {
        nullableBinding = null
        super.onDestroyView()
    }
}