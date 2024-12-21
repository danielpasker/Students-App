package com.example.students_app.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.students_app.R
import com.example.students_app.model.Student
import com.example.students_app.ui.adapters.StudentAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StudentAdapter
    private lateinit var studentActivityLauncher: ActivityResultLauncher<Intent>

    companion object {
        val studentList = mutableListOf<Student>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Students List"

        studentActivityLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    adapter.notifyDataSetChanged()
                }
            }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        if (studentList.isEmpty()) {
            studentList.add(Student(212810675, "Daniel Parker", "123-456-7890", "Address 1", false))
            studentList.add(Student(301344946, "Lorem ipsum", "987-654-3210", "Address 2", true))
        }

        adapter = StudentAdapter(studentList) { student ->
            val intent = Intent(this, StudentActivity::class.java).apply {
                putExtra("student_id", student.id)
            }
            studentActivityLauncher.launch(intent)
        }
        recyclerView.adapter = adapter

        findViewById<FloatingActionButton>(R.id.fab).apply {
            setOnClickListener {
                studentActivityLauncher.launch(
                    Intent(
                        this@MainActivity,
                        StudentActivity::class.java
                    )
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == RESULT_OK) {
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}