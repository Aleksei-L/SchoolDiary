package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R
import com.schooldiary.data.grade.GradeResponseItem

class ReportAdapter2(
    private val items: List<GradeResponseItem>
) : RecyclerView.Adapter<ReportAdapter2.ReportViewHolder2>() {
    class ReportViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val subjectName: TextView = itemView.findViewById(R.id.report_card_subject_name2)
        val reportAverage: TextView = itemView.findViewById(R.id.report_card_average2)
        val marks: RecyclerView = itemView.findViewById(R.id.rv_report_marks2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder2 {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.report_item2, parent, false)
        return ReportViewHolder2(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder2, position: Int) {
        val item = items[position]
        holder.subjectName.text = item.name
        val average = item.grade.map { it.value }.average()
        holder.reportAverage.prepareForShowMark(average)


        holder.marks.apply {
            layoutManager = GridLayoutManager(context, 5) //TODO
        }
    }
    private fun TextView.prepareForShowMark(mark: Double) {
        this.text = mark.toString()
        when (mark) {
            in 2.0..<3.0 -> this.setTextColor(context.getColor(R.color.mark_red))
            in 3.0..<4.0 -> this.setTextColor(context.getColor(R.color.mark_yellow))
            in 4.0..<5.0 -> this.setTextColor(context.getColor(R.color.mark_dark_green))
            5.0 -> this.setTextColor(context.getColor(R.color.mark_green))
            else -> this.setTextColor(context.getColor(R.color.mark_neutral))
        }
    }
    override fun getItemCount() = items.size
}