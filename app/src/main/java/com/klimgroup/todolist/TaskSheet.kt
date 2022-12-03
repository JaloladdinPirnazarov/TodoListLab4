package com.klimgroup.todolist

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.timepicker.MaterialTimePicker
import com.klimgroup.todolist.Others.Actions
import com.klimgroup.todolist.Others.Constants
import com.klimgroup.todolist.Others.FunctionsManager
import com.klimgroup.todolist.Others.TaskItems
import com.klimgroup.todolist.databinding.FragmentTaskSheetBinding
import java.time.LocalTime

class TaskSheet(
    private var task: TaskItems,
    private val actions: Actions
    ) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentTaskSheetBinding
    private val funcManager = FunctionsManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskSheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        setOnClickListeners()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setOnClickListeners(){
        binding.btnSetStartTime.setOnClickListener{ timePicker(1) }
        binding.btnSetEndTime.setOnClickListener{ timePicker(2) }
        binding.btnSave.setOnClickListener { passData() }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun timePicker(n:Int){
        val dialog = TimePickerDialog(activity,{_,hour,minute->
            if (n == 1){
                task.startTime = "$hour:$minute"
                binding.btnSetStartTime.text = "Start time: ${task.startTime}"
            }else{
                task.endTime = "$hour:$minute"
                binding.btnSetEndTime.text = "Start time: ${task.endTime}"
            }
        }, LocalTime.now().hour,LocalTime.now().minute,false)
        dialog.show()
    }

    @SuppressLint("SetTextI18n")
    private fun setData(){
        if (task.isEdit){
            binding.apply {
                task.editedDate = funcManager.getDate()
                tvState.text = "Edit Task"
                edTitle.setText(task.title)
                if (task.desc.isNotEmpty()) edDesc.setText(task.desc)
                if (task.startTime.isNotEmpty()) btnSetStartTime.text = "Start ${task.startTime}"
                if (task.endTime.isNotEmpty()) btnSetEndTime.text = "End ${task.startTime}"
            }
        }
    }

    private fun passData(){
        binding.apply {
            task.title = edTitle.text.toString()
            task.desc = edDesc.text.toString()

            if (task.title.isEmpty())
                edTitle.error = "Fill the field!"
            else{
                edTitle.setText("")
                edDesc.setText("")
                actions.getTask(task)
                dismiss()
            }

        }
    }

}