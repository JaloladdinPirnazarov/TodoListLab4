package com.klimgroup.todolist

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.klimgroup.todolist.DataBase.DbManager
import com.klimgroup.todolist.Others.*
import com.klimgroup.todolist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), Actions{
    private lateinit var binding: ActivityMainBinding
    private lateinit var actions: Actions
    private lateinit var rcAdapter: RCViewAdapter
    private val funcManager = FunctionsManager()
    private lateinit var dbManager: DbManager

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initVariables()
        setOnClickListeners()
        dbManager.openDb()
        updateAdapter()
    }

    private fun initVariables(){
        dbManager = DbManager(this)
        actions = this
        rcAdapter = RCViewAdapter(ArrayList(),dbManager,actions,this)
        binding.rcTasks.layoutManager = LinearLayoutManager(this)
        binding.rcTasks.adapter = rcAdapter
    }

    @SuppressLint("SuspiciousIndentation")
    private fun setOnClickListeners(){
        binding.btnNewTask.setOnClickListener {
            val task = TaskItems()
            task.createdDate = funcManager.getDate()
            showTaskSheet(task)
        }
    }

    override fun getTask(task: TaskItems) {
        val message = if (task.isEdit) {
            dbManager.updateTask(task)
            "Task edited successfully"
        } else {
            dbManager.insertTask(task)
            "Task added successfully"
        }
        updateAdapter()
        funcManager.toastMessage(message,this)
    }

    override fun editTask(task: TaskItems) {
        task.isEdit = true
        showTaskSheet(task)
    }

    override fun deleteTask(id: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setIcon(R.drawable.ic_warning)
        builder.setTitle("Warning")
        builder.setMessage("Are you sure you want to delete this task. It can't be returned later.")
        builder.setPositiveButton("yes"){_,_->
            dbManager.deleteTask(id)
            updateAdapter()
            funcManager.toastMessage("Task deleted successfully",this)
        }
        builder.setNegativeButton("no"){_,_->}
        builder.show()
    }

    private fun showTaskSheet(task: TaskItems){
        TaskSheet(task,actions).show(supportFragmentManager,Constants.TASK_TAG)
    }

    private fun updateAdapter(){
        val tasks = dbManager.getTasks()
        binding.tvEmpty.visibility = if (tasks.size == 0) View.VISIBLE
        else View.GONE
        rcAdapter.updateAdapter(tasks)
    }


}