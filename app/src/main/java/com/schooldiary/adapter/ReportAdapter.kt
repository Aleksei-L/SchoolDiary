package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R
import com.schooldiary.data.grade.GradeResponseItem

class ReportAdapter(
    private val items: List<GradeResponseItem>
) : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {
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
        val item = items[position]
        holder.subjectName.text = item.name
        val average = item.grade.map { it.value }.average()
        holder.reportAverage.text = "СР ${"%.1f".format(average)}"

        holder.marks.apply {
            adapter = ReportCardAdapter(item.grade, false)
            layoutManager = GridLayoutManager(context, 5)
        }
    }

    override fun getItemCount() = items.size
}
