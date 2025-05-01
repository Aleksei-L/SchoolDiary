package com.schooldiary.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.schooldiary.R
import com.schooldiary.data.grade.Grade
import com.schooldiary.data.grade.Student
import com.schooldiary.data.student.AllStudentsResponseItem
import com.schooldiary.viewmodel.MainViewModel

class ReportForTeacherAdapter(
    private val items: MutableMap<Student, List<Grade>>,
    private val fragmentManager: FragmentManager,
    private val viewModel: MainViewModel
) : RecyclerView.Adapter<ReportForTeacherAdapter.ReportViewHolder>() {
    class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studentName: TextView = itemView.findViewById(R.id.report_card_subject_name)
        val reportAverage: TextView = itemView.findViewById(R.id.report_card_average)
        val marks: RecyclerView = itemView.findViewById(R.id.rv_report_marks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.report_item, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val item = items.getItemByPosition(position)

        holder.studentName.text = item.first.name
        val average = item.second.map { it.value }.average()
        holder.reportAverage.text = "СР ${"%.1f".format(average)}"

        holder.marks.apply {
            layoutManager = GridLayoutManager(context, 5)
            adapter = ReportCardAdapter(
                item.second.sortedBy { it.data },
                true,
                fragmentManager,
                viewModel,
                item.first.name
            )
        }
    }

    override fun getItemCount() = items.size

    private fun Map<Student, List<Grade>>.getItemByPosition(position: Int): Pair<Student, List<Grade>> {
        var counter = 0
        for (i in this) {
            if (counter == position)
                return Pair(i.key, i.value)
            counter++
        }
        throw Exception("getItemByPosition")
    }

    fun addMoreStudents(students: List<AllStudentsResponseItem>) {
        students.forEach {
            items.plus(Pair(Student(it.user.name, it.studentId), listOf()))
        }
    }
}
