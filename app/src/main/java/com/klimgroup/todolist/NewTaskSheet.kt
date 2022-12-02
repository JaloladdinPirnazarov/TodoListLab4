package com.klimgroup.todolist

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.klimgroup.todolist.databinding.FragmentNewTaskSheetBinding
import java.time.LocalTime

class NewTaskSheet(var taskItem:TaskItem?) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentNewTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()
        val dueTimeString =
        if (taskItem != null){
            binding.tvTaskTitle.text = "Edit Task"
            binding.edName.setText(taskItem!!.name)
            binding.edDesc.setText(taskItem!!.desc)
            if (taskItem!!.dueTime() != null){
                dueTime = taskItem!!.dueTime()!!
                updateTimeButtonText()
            }
        }else{
            binding.tvTaskTitle.text = "New Task"
        }
        taskViewModel = ViewModelProvider(activity)[TaskViewModel::class.java]
        binding.btnSave.setOnClickListener { save() }
        binding.timePickerButton.setOnClickListener { openTimePicker() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun openTimePicker() {
        if (dueTime == null){
            dueTime = LocalTime.now()
            val listener = TimePickerDialog.OnTimeSetListener{_,hour,minute->
                dueTime = LocalTime.of(hour,minute)
                updateTimeButtonText()
            }
            val dialog = TimePickerDialog(activity,listener,dueTime!!.hour,dueTime!!.minute,true)
            dialog.setTitle("Task Due")
            dialog.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateTimeButtonText() {
        binding.timePickerButton.text = String.format("%02d:%02d",dueTime!!.hour,dueTime!!.minute)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewTaskSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    private fun save(){
        binding.apply {
            val name = edName.text.toString()
            val desc = edDesc.text.toString()
            val dueTimeString = if (dueTime == null) null else TaskItem.timeFormatter.format(dueTime)
            if (taskItem == null){
                val newTask = TaskItem(name,desc,dueTimeString,null)
                taskViewModel.addTaskItem(newTask)
            }else{
                taskViewModel.updateTaskItem(taskItem!!.id)
            }
            edName.setText("")
            edDesc.setText("")
        }
        dismiss()
    }


}