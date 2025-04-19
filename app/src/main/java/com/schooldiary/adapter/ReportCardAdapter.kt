package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R
import com.schooldiary.data.grade.Grade

class ReportCardAdapter(
    private val items: List<Grade>
) : RecyclerView.Adapter<ReportCardAdapter.ReportCardViewHolder>() {
    class ReportCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayOfMonth: TextView = itemView.findViewById(R.id.day_of_month)
        val mark: TextView = itemView.findViewById(R.id.mark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportCardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.report_card_item, parent, false)
        return ReportCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportCardViewHolder, position: Int) {
        val item = items[position]
        val day = item.data.subSequence(8, 10)
        val month = item.data.subSequence(5, 7)
        holder.dayOfMonth.text = "$day.$month"
        holder.mark.prepareForShowMark(item.value)
    }

    override fun getItemCount() = items.size

    private fun TextView.prepareForShowMark(mark: Int) {
        this.text = mark.toString()
        when (mark) {
            2 -> this.setTextColor(context.getColor(R.color.mark_red))
            3 -> this.setTextColor(context.getColor(R.color.mark_yellow))
            4 -> this.setTextColor(context.getColor(R.color.mark_dark_green))
            5 -> this.setTextColor(context.getColor(R.color.mark_green))
            else -> this.setTextColor(context.getColor(R.color.mark_neutral))
        }
    }
}
