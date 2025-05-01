package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R
import com.schooldiary.data.schedule.Lesson

class LessonPreviewAdapter2(
    private val items: List<Lesson>,
) : RecyclerView.Adapter<LessonPreviewAdapter2.LessonPreviewViewHolder2>() {
    class LessonPreviewViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lessonTitle: TextView = itemView.findViewById(R.id.lesson_title2)
        val teacherName: TextView = itemView.findViewById(R.id.teacher_name2)
        val roomName: TextView = itemView.findViewById(R.id.room_name)
        val lessonTime: TextView = itemView.findViewById(R.id.lesson_time2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonPreviewViewHolder2 {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.lesson_preview_item2, parent, false)
        return LessonPreviewViewHolder2(view)
    }

    override fun onBindViewHolder(holder: LessonPreviewViewHolder2, position: Int) {
        val item = items[position]
        holder.lessonTitle.text = "${item.lessonOrder}. ${item.subjectName}"
        holder.teacherName.text = item.teacherName
        holder.roomName.text = item.room
        holder.lessonTime.text = "${item.startTime.dropLast(3)} - ${item.endTime.dropLast(3)}"
    }

    override fun getItemCount() = items.size
}
