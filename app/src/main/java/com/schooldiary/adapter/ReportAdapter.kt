package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R

class ReportAdapter : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {
    class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subjectName: TextView = itemView.findViewById(R.id.report_card_subject_name)
        val reportAverage: TextView = itemView.findViewById(R.id.report_card_average)
        val marks: RecyclerView = itemView.findViewById(R.id.rv_report_marks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.report_item, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.subjectName.text = "Алгебра"
        holder.reportAverage.text = "СР 5"

        holder.marks.apply {
            adapter = ReportCardAdapter()
            layoutManager = GridLayoutManager(context, 5) //TODO
        }
    }

    override fun getItemCount() = 5 //TODO
}
