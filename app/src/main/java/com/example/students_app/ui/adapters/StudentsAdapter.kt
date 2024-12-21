package com.example.students_app.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.students_app.R
import com.example.students_app.model.Student
import com.example.students_app.utils.StudentUtils

class StudentAdapter(
    private val studentList: List<Student>,
    private val onItemClick: (Student) -> Unit
) : RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.studentName)
        val idTextView: TextView = itemView.findViewById(R.id.studentId)
        val checkBox: CheckBox = itemView.findViewById(R.id.studentCheckbox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_item, parent, false)
        return StudentViewHolder(view)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = studentList[position]
        holder.nameTextView.text = student.name
        holder.idTextView.text = student.id.toString()
        holder.checkBox.isChecked = student.isChecked
        holder.itemView.setOnClickListener { onItemClick(student) }
        holder.checkBox.setOnCheckedChangeListener { _, _ ->
            StudentUtils.onCheckStudent(
                holder.checkBox, student.id
            )
        }
    }

    override fun getItemCount() = studentList.size
}