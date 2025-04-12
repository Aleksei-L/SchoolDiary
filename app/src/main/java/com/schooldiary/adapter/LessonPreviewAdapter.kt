package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R

class LessonPreviewAdapter(
    private val lessonCounter: Int,
    private val title: String,
    private val hasHomework: Boolean,
    private val time: String,
    private val onClick: () -> Unit
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
        holder.lessonTitle.text = title
        holder.homeworkMarker.visibility = if (hasHomework) View.VISIBLE else View.INVISIBLE
        holder.lessonTime.text = time
        holder.itemView.setOnClickListener { onClick() }
    }

    override fun getItemCount() = lessonCounter
}
