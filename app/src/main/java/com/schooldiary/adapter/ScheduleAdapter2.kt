package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R
import com.schooldiary.data.schedule.Schedule

class ScheduleAdapter2(
    private val items: List<Schedule>
) : RecyclerView.Adapter<ScheduleAdapter2.ScheduleViewHolder2>() {
    private var onClick: ((Int) -> Unit)? = null

    class ScheduleViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.header_title2)
        val lessons: RecyclerView = itemView.findViewById(R.id.rv_preview_lessons2)
        val addButton: Button = itemView.findViewById(R.id.New_Lesson)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder2 {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_item2, parent, false)
        return ScheduleViewHolder2(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder2, position: Int) {
        val item = items[position]

        holder.title.text = item.weekDayName.toString()
        holder.lessons.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = LessonPreviewAdapter2(
                item.lessons,
            )
        }
    }

    override fun getItemCount() = items.size

    fun setOnClickListener(action: (Int) -> Unit) {
        onClick = action
    }
}
