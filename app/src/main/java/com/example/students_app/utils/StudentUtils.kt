package com.example.students_app.utils

import android.view.View
import android.widget.CheckBox
import com.example.students_app.ui.MainActivity.Companion.studentList

class StudentUtils {
    companion object {
        fun onCheckStudent(view: View, studentId: Int) {
            if (view !is CheckBox) return

            val studentIndex = studentList.indexOfFirst { it.id == studentId }

            if (studentIndex != -1) {
                studentList[studentIndex].isChecked = view.isChecked
            }
        }
    }
}