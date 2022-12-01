package com.klimgroup.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.klimgroup.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var taskViewModel: TaskViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        taskViewModel = ViewModelProvider(this)[TaskViewModel::class.java]

        binding.newTaskBtn.setOnClickListener {
            NewTaskSheet().show(supportFragmentManager,"newTaskSheet")
        }

        taskViewModel.name.observe(this){
            binding.tvTaskName.text = String.format("Task Name: %s",it)
        }

        taskViewModel.desc.observe(this){
            binding.tvDesc.text = String.format("Task Desc: %s",it)
        }
    }
}