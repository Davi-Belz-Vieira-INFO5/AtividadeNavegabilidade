package com.davi.atividade1.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davi.atividade1.R
import com.davi.atividade1.data.model.Status
import com.davi.atividade1.data.model.Task
import com.davi.atividade1.databinding.DoneFragmentBinding
import com.davi.atividade1.ui.adapter.TaskAdapter

class DoneFragment : Fragment() {

    private var _binding: DoneFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DoneFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initlisteners()

        initRecyclerViewTask(getTask())
    }

    private fun initlisteners(){
        binding.floatingActionButton2.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment2)
        }
    }

    private fun initRecyclerViewTask(taskList: List<Task>){

        taskAdapter = TaskAdapter(requireContext(), taskList) { task, option -> optionSelected(task, option)}
        binding.recyclerViewTask.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTask.setHasFixedSize(true)
        binding.recyclerViewTask.adapter = taskAdapter
    }

    private fun optionSelected(task: Task, option:Int){
        when (option){
            TaskAdapter.SELECT_REMOVER -> {
                Toast.makeText(requireContext(), "Removendo ${task.description}", Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECT_EDIT -> {
                Toast.makeText(requireContext(), "Editando ${task.description}", Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECT_DETAILS -> {
                Toast.makeText(requireContext(), "Detalhes ${task.description}", Toast.LENGTH_SHORT).show()
            }
            TaskAdapter.SELECT_BACK -> {
                Toast.makeText(requireContext(), "Anterior", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTask() = listOf(
        Task("0", "Criar nova tela do app", Status.DONE),
        Task("1", "Validar informações na tela de login", Status.DONE),
        Task("2", "Adicionar nova funcionalidade no app", Status.DONE),
        Task("3", "Salvar token localmente", Status.DONE),
        Task("2", "Criar funcionalidades no logout no app", Status.DONE),
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}