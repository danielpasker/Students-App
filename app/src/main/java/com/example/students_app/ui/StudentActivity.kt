package com.example.students_app.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.students_app.R
import com.example.students_app.model.Student
import com.example.students_app.ui.MainActivity.Companion.studentList
import com.example.students_app.utils.StudentUtils

class StudentActivity : AppCompatActivity() {
    private lateinit var idEditText: EditText
    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var studentChecked: CheckBox
    private lateinit var editButton: Button
    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button
    private lateinit var deleteButton: Button

    private var studentId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        idEditText = findViewById(R.id.idEditText)
        nameEditText = findViewById(R.id.nameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)
        addressEditText = findViewById(R.id.addressEditText)
        studentChecked = findViewById(R.id.studentChecked)
        editButton = findViewById(R.id.editButton)
        cancelButton = findViewById(R.id.cancelButton)
        saveButton = findViewById(R.id.saveButton)
        deleteButton = findViewById(R.id.deleteButton)

        studentId = intent.getIntExtra("student_id", -1).takeIf { it != -1 }

        if (studentId != null) {
            val student = studentList.find { it.id == studentId }

            if (student != null) {
                supportActionBar?.title = getString(R.string.students_details_title)

                idEditText.setText(student.id.toString())
                nameEditText.setText(student.name)
                phoneEditText.setText(student.phone)
                addressEditText.setText(student.address)
                studentChecked.isChecked = student.isChecked
                studentChecked.setOnCheckedChangeListener { _, _ ->
                    StudentUtils.onCheckStudent(
                        studentChecked, student.id
                    )
                }
            }

            setEditMode(false)
        } else {
            setEditMode(true)
            supportActionBar?.title = getString(R.string.new_student_title)

            editButton.visibility = View.GONE
            cancelButton.visibility = View.GONE
            deleteButton.visibility = View.GONE
        }
    }

    private fun setEditMode(isEditMode: Boolean) {
        idEditText.isEnabled = isEditMode
        nameEditText.isEnabled = isEditMode
        phoneEditText.isEnabled = isEditMode
        addressEditText.isEnabled = isEditMode

        if (isEditMode) {
            handleEdit()
        } else {
            editButton.visibility = View.VISIBLE
            cancelButton.visibility = View.GONE
            saveButton.visibility = View.GONE
            deleteButton.visibility = View.VISIBLE

            editButton.setOnClickListener {
                setEditMode(true)
                supportActionBar?.title = getString(R.string.edit_student_title)
            }

            deleteButton.setOnClickListener {
                val studentIndex = studentList.indexOfFirst { it.id == studentId }

                if (studentIndex != -1) {
                    studentList.removeAt(studentIndex)
                }

                setResult(RESULT_OK)
                finish()
            }
        }
    }

    private fun handleEdit() {
        saveButton.visibility = View.VISIBLE
        cancelButton.visibility = View.VISIBLE
        editButton.visibility = View.GONE
        deleteButton.visibility = View.GONE

        saveButton.setOnClickListener {
            val id = idEditText.text.toString().toInt()
            val name = nameEditText.text.toString()
            val phone = phoneEditText.text.toString()
            val address = addressEditText.text.toString()
            val isChecked = studentChecked.isChecked
            val isEditingStudent = studentId != null

            if (isEditingStudent) {
                val studentIndex = studentList.indexOfFirst { it.id == studentId }

                if (studentIndex != -1) {
                    studentList[studentIndex] = Student(
                        id,
                        name,
                        phone,
                        address,
                        isChecked
                    )
                }
            } else {
                val newStudent = Student(id, name, phone, address, isChecked)
                studentList.add(newStudent)
            }

            setResult(RESULT_OK)
            finish()
        }

        cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            this.setEditMode(false)
            supportActionBar?.title = getString(R.string.students_details_title)
        }
    }
}