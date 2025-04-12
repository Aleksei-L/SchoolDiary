package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R

class ReportCardAdapter : RecyclerView.Adapter<ReportCardAdapter.ReportCardViewHolder>() {
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
        holder.dayOfMonth.text = "12.12"
        holder.mark.text = "4"
    }

    override fun getItemCount() = 20 //TODO
}
