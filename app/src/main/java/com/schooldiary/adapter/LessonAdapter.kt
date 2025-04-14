package com.schooldiary.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R

class LessonAdapter(
    private val items: List<Any>, //TODO
    private val context: Context
) : RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {
    class LessonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lessonTitle: TextView = itemView.findViewById(R.id.details_lesson_title)
        val lessonTime: TextView = itemView.findViewById(R.id.details_lesson_time)
        val teacherName: TextView = itemView.findViewById(R.id.teacher_name)
        val roomNumber: TextView = itemView.findViewById(R.id.room_number)
        val homework: TextView = itemView.findViewById(R.id.details_homework)
        val mark: TextView = itemView.findViewById(R.id.mark)
        //val homeworkEditable: TextView = itemView.findViewById(R.id.details_homework_editable)
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

        holder.mark.text = "5"
        holder.mark.setTextColor(context.getColor(R.color.mark_green))

        //holder.homework.visibility = if (/*ученик*/false) View.VISIBLE else View.GONE
        //holder.homeworkEditable.visibility = if (/*ученик*/false) View.GONE else View.VISIBLE
    }

    override fun getItemCount() = items.size
}
