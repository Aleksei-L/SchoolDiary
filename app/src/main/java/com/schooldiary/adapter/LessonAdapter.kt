package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R
import com.schooldiary.data.schedule.Lesson
import com.schooldiary.fragment.BottomSheetFragment

class LessonAdapter(
    private var items: List<Lesson>?,
    private val isUserTeacher: Boolean,
    private val fragmentManager: FragmentManager,
    private val onLessonClick: (String, String) -> Unit
) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {
    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lessonTitle: TextView = itemView.findViewById(R.id.details_lesson_title)
        val lessonTime: TextView = itemView.findViewById(R.id.details_lesson_time)
        val teacherName: TextView = itemView.findViewById(R.id.teacher_name)
        val roomNumber: TextView = itemView.findViewById(R.id.room_number)
        val homework: TextView = itemView.findViewById(R.id.details_homework)
        val teacherHomework: ImageView = itemView.findViewById(R.id.teacher_homework_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lesson_item, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val item = items?.get(position) ?: return

        holder.lessonTitle.text = item.subjectName
        holder.lessonTime.text = "${item.startTime.dropLast(3)} - ${item.endTime.dropLast(3)}"
        holder.teacherName.text = item.teacherName
        holder.roomNumber.text = item.room
        holder.homework.text = item.homework

        if (isUserTeacher) {
            holder.teacherHomework.visibility = View.VISIBLE
            if (holder.homework.text.isEmpty()) holder.homework.text = "Домашнее задание не задано"
            holder.teacherHomework.setOnClickListener {
                onLessonClick(item.lessonId, item.homework)
                val bottomSheet = BottomSheetFragment()
                bottomSheet.show(fragmentManager, bottomSheet.tag)
            }
        }
    }

    override fun getItemCount() = items?.size ?: 0

    fun updateHomework(lessonId: String, newHomework: String) {
        items = items?.map { lesson ->
            if (lesson.lessonId == lessonId) {
                lesson.copy(
                    homework = newHomework, teacherName = lesson.teacherName ?: ""
                )
            } else {
                lesson
            }
        }
        notifyDataSetChanged()
    }
}
