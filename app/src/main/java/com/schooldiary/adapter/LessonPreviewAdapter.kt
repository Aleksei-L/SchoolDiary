package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R
import com.schooldiary.data.schedule.Lesson

class LessonPreviewAdapter(
    private val items: List<Lesson>,
    private val parentPosition: Int,
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<LessonPreviewAdapter.LessonPreviewViewHolder>() {
    class LessonPreviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lessonTitle: TextView = itemView.findViewById(R.id.lesson_title)
        val homeworkMarker: ImageView = itemView.findViewById(R.id.homework_marker)
        val lessonTime: TextView = itemView.findViewById(R.id.lesson_time)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonPreviewViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.lesson_preview_item, parent, false)
        return LessonPreviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: LessonPreviewViewHolder, position: Int) {
        val item = items[position]
        holder.lessonTitle.text = item.subjectName
        holder.homeworkMarker.visibility =
            if (item.homework != null && item.homework.isNotEmpty()) View.VISIBLE else View.INVISIBLE
        holder.lessonTime.text = "${item.startTime.dropLast(3)} - ${item.endTime.dropLast(3)}"
        holder.itemView.setOnClickListener { onClick(parentPosition) }
    }

    override fun getItemCount() = items.size
}
