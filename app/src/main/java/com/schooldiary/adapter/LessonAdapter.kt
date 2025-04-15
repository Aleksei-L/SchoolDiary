package com.schooldiary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R
import com.schooldiary.data.Lesson

class LessonAdapter(
    private val items: List<Lesson>?,
    private val context: Context
) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {
    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lessonTitle: TextView = itemView.findViewById(R.id.details_lesson_title)
        val lessonTime: TextView = itemView.findViewById(R.id.details_lesson_time)
        val teacherName: TextView = itemView.findViewById(R.id.teacher_name)
        val roomNumber: TextView = itemView.findViewById(R.id.room_number)
        val homework: TextView = itemView.findViewById(R.id.details_homework)
        val mark: TextView = itemView.findViewById(R.id.mark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.lesson_item, parent, false)
        return LessonViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val item = items?.get(position) ?: return
        holder.lessonTitle.text = item.subjectName
        holder.lessonTime.text = "${item.startTime.dropLast(3)} - ${item.endTime.dropLast(3)}"
        holder.teacherName.text = item.teacherName
        holder.roomNumber.text = item.room
        holder.homework.text = item.homework
        //holder.mark.text = "5"
        //holder.mark.setTextColor(context.getColor(R.color.mark_green))
    }

    override fun getItemCount() = items?.size ?: 0
}
