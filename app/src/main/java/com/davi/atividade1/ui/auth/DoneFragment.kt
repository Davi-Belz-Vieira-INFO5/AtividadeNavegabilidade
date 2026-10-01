package com.davi.atividade1.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.davi.atividade1.R
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

        taskAdapter = TaskAdapter(taskList)
        binding.recyclerViewTask.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTask.setHasFixedSize(true)

        binding.recyclerViewTask.adapter = taskAdapter
    }

    private fun getTask() = listOf(
        Task("0", "Criar nova tela do app"),
        Task("1", "Validar informações na tela de login"),
        Task("2", "Adicionar nova funcionalidade no app"),
        Task("3", "Salvar token localmente"),
        Task("2", "Criar funcionalidades no logout no app"),
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}