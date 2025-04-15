package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R
import com.schooldiary.data.ScheduleResponse

class ScheduleAdapter(
    private val items: ScheduleResponse
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {
    private var onClick: ((Int) -> Unit)? = null

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.header_title)
        val lessons: RecyclerView = itemView.findViewById(R.id.rv_preview_lessons)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.weekDayName
        holder.itemView.setOnClickListener {
            onClick?.let { it(position) }
        }
        holder.lessons.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = LessonPreviewAdapter(
                item.lessons,
                position,
                onClick!!
            )
        }
    }

    override fun getItemCount() = items.size

    fun setOnClickListener(action: (Int) -> Unit) {
        onClick = action
    }
}
