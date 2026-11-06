package com.davi.atividade1.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.davi.atividade1.R
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davi.atividade1.data.model.Status
import com.davi.atividade1.data.model.Task

import com.davi.atividade1.databinding.TodoFragmentBinding
import com.davi.atividade1.ui.TaskViewModel
import com.davi.atividade1.ui.adapter.TaskAdapter
import com.davi.atividade1.ui.util.showBottomSheet
import com.davi.atividade1.util.FireBaseHelper
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class TodoFragment: Fragment() {
    private var _binding : TodoFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = TodoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initlisteners()
        initRecyclerViewTask()
        getTask()
    }
    private fun initlisteners(){
        binding.floatingActionButton2.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFormTaskFragment2(null)
            findNavController().navigate(action)
        }

        observerViewModel()
    }

    private fun observerViewModel(){
        viewModel.taskUpdate.observe(viewLifecycleOwner){ updateTask ->
            if (updateTask.status == Status.TODO){

                //armazena a lista atual do adaptador
                val oldList = taskAdapter.currentList

                //gera uma nova lista a partir da lista antiga já com a tarefa atualizada
                val newList = oldList.toMutableList().apply {
                    find { it.id == updateTask.id }?.description = updateTask.description
                }

                //armazena a posição da tarefa a ser atualizada na lista
                val position = newList.indexOfFirst { it.id == updateTask.id }

                //envia a lista atualizada para o adapter
                taskAdapter.submitList(newList)

                //atualiza a tarefa pela posição do adapter
                taskAdapter.notifyItemChanged(position)
            }
        }
    }

    private fun initRecyclerViewTask(){
        taskAdapter = TaskAdapter(requireContext(), ) { task, option -> optionSelected(task, option)}

        with(binding.recyclerViewTask){
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = taskAdapter
        }
    }

    private fun optionSelected(task: Task, option:Int){
        when (option){
            TaskAdapter.SELECT_REMOVER -> {

                showBottomSheet(titleDialog = R.string.text_title_dialog_delete,
                    message = getString(R.string.text_message_dialog_delete),
                    titleButton = R.string.text_button_dialog_confirm,
                    onClick = {
                        deleteTask(task)
                    }
                )

            }
            TaskAdapter.SELECT_EDIT -> {
                val action = HomeFragmentDirections.actionHomeFragmentToFormTaskFragment2(task)
                findNavController().navigate(action)
            }
            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(requireContext(), "Detalhes ${task.description}", Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECT_FORWARD -> {
                Toast.makeText(requireContext(), "Próximo", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTask() {
        FireBaseHelper.getDatabase()
            .child("task")
            .child(FireBaseHelper.getIdUser())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    val taskList= mutableListOf<Task>()

                    for (ds in p0.children){
                        val task = ds.getValue(Task::class.java) as Task
                        if (task.status == Status.TODO) {
                            taskList.add(task)
                        }
                    }
                    binding.progressBar.isVisible=false
                    listEmpty(taskList)

                    taskList.reverse()
                    taskAdapter.submitList(taskList)
                }

                override fun onCancelled(p0: DatabaseError) {
                    Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun deleteTask(task: Task){
        FireBaseHelper.getDatabase()
            .child("task")
            .child(FireBaseHelper.getIdUser())
            .child(task.id)
            .removeValue().addOnCompleteListener { result ->
                if (result.isSuccessful){
                    Toast.makeText(requireContext(), R.string.text_delete_sucess_task, Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(requireContext(), R.string.error_generic, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun listEmpty(taskList: List<Task>){
        binding.textInfo.text = if (taskList.isEmpty()){
            getString(R.string.text_list_task_empty)
        }else{
            ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}