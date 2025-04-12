package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R

class LessonAdapter(
    private val items: List<Any> //TODO
) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {
    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lessonTitle: TextView = itemView.findViewById(R.id.details_lesson_title)
        val lessonTime: TextView = itemView.findViewById(R.id.details_lesson_time)
        val teacherName: TextView = itemView.findViewById(R.id.teacher_name)
        val roomNumber: TextView = itemView.findViewById(R.id.room_number)
        val homework: TextView = itemView.findViewById(R.id.details_homework)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.lesson_item, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val item = items[position]
        holder.lessonTitle.text = "sadasdasd"
        holder.lessonTime.text = "12:12 - 12:21"
        holder.teacherName.text = "asdasdasd"
        holder.roomNumber.text = "123"
        holder.homework.text = "asdasdasd"
    }

    override fun getItemCount() = items.size
}
