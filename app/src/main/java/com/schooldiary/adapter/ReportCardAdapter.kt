package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R
import com.schooldiary.data.grade.Grade
import com.schooldiary.fragment.AddMarkBottomSheetFragment
import com.schooldiary.viewmodel.MainViewModel

class ReportCardAdapter(
    private val items: List<Grade>,
    private val isTeacher: Boolean,
    private val fragmentManager: FragmentManager? = null,
    private val viewModel: MainViewModel? = null,
    private val studentName: String? = null
) : RecyclerView.Adapter<ReportCardAdapter.ReportCardViewHolder>() {
    class ReportCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayOfMonth: TextView = itemView.findViewById(R.id.day_of_month)
        val mark: TextView = itemView.findViewById(R.id.mark)
        val addMark: ImageView = itemView.findViewById(R.id.add_mark)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportCardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.report_card_item, parent, false)
        return ReportCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportCardViewHolder, position: Int) {
        if (position == items.size) {
            holder.dayOfMonth.visibility = View.GONE
            holder.mark.visibility = View.GONE
            holder.addMark.visibility = View.VISIBLE
            holder.itemView.setOnClickListener {
                viewModel?.studentNameForTeacherNewMark = studentName ?: ""
                val bottomSheet = AddMarkBottomSheetFragment()
                bottomSheet.show(fragmentManager!!, bottomSheet.tag)
            }
            return
        }

        val item = items[position]
        val day = item.data.subSequence(8, 10)
        val month = item.data.subSequence(5, 7)
        holder.dayOfMonth.text = "$day.$month"
        holder.mark.prepareForShowMark(item.value)
    }

    override fun getItemCount() = if (isTeacher) items.size + 1 else items.size

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
