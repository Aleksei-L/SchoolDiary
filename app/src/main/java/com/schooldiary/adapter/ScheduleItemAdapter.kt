package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R
import com.schooldiary.data.ScheduleItems

class ScheduleAdapter(private val items: List<ScheduleItems>) :
    RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    class ScheduleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val day: TextView = itemView.findViewById(R.id.day)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        return ScheduleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val item = items[position]
        holder.day.text = item.day
    }

    override fun getItemCount() = items.size
}