package com.projects.todolist.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.projects.todolist.R
import com.projects.todolist.databinding.FragmentAddTodoPopupBinding
import com.projects.todolist.utils.ToDoData


class AddTodoPopupFragment : DialogFragment() {
    private lateinit var binding:FragmentAddTodoPopupBinding
    private lateinit var listener: DialogNextBtnClickListener
    private var toDoData: ToDoData? = null

    fun setListener(listener: DialogNextBtnClickListener){
        this.listener = listener
    }

    companion object{
        const val TAG = "AddTodoPopupFragment"

        @JvmStatic
        fun newInstance(taskId:String, task: String) = AddTodoPopupFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddTodoPopupBinding.inflate(inflater, container, false)


        if (arguments!=null){
            toDoData = ToDoData(arguments?.getString("taskId").toString(), arguments?.getString("task").toString())
            binding.todoEt.setText(toDoData?.task)
        }
        resgisterEvents()

        return binding.root
    }

    private fun resgisterEvents() {
        binding.todoNextBtn.setOnClickListener{
            val todoTask = binding.todoEt.text.toString()
            if(todoTask.isNotEmpty()){
                if (toDoData==null){
                    listener.onSaveTask(todoTask, binding.todoEt)
                }else{
                    toDoData?.task = todoTask
                    listener.onUpdateTask(toDoData!!, binding.todoEt)
                }

            }else{
                Toast.makeText(context, "Please type some task", Toast.LENGTH_LONG).show()
            }
        }

        binding.todoClose.setOnClickListener{
            dismiss()
        }

    }

    interface DialogNextBtnClickListener{
        fun onSaveTask(todo: String, todoEt: TextInputEditText)
        fun onUpdateTask(toDoData: ToDoData, todoEt: TextInputEditText)
    }

}