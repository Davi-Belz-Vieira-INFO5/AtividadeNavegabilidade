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
        initRecyclerViewTask()
        getTask()
    }

    private fun initlisteners(){
        binding.floatingActionButton2.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_formTaskFragment2)
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

    private fun getTask() {
        val taskList = listOf(
            Task("0", "salvar projeto no Github", Status.DONE),
            Task("1", "Validar informações na tela de login", Status.DONE),
            Task("2", "Adicionar novas funcionalidade no app", Status.DONE),
            Task("3", "Salvar dados do usuário", Status.DONE),
            Task("2", "Criar funcionalidades no login no app", Status.DONE),
        )
        taskAdapter.submitList(taskList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}