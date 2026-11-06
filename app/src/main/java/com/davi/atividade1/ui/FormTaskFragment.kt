package com.davi.atividade1.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.davi.atividade1.R
import com.davi.atividade1.data.model.Status
import com.davi.atividade1.data.model.Task
import com.davi.atividade1.databinding.FragmentFormTaskBinding
import com.davi.atividade1.ui.util.initToolbar
import com.davi.atividade1.ui.util.showBottomSheet
import com.davi.atividade1.util.FireBaseHelper
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database


class FormTaskFragment : BaseFragment() {

    private var _binding: FragmentFormTaskBinding? = null
    private val binding get() = _binding!!

    //alocacao de memória para a variável e instanciar mais tarde
    private lateinit var task: Task

    private var newTask: Boolean = true

    private var status: Status = Status.TODO

    private val viewModel: TaskViewModel by activityViewModels()

    private val args: FormTaskFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFormTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        getArgs()
        initListener()
    }

    private fun getArgs(){
        args.task.let {
            if (it != null){
                this.task = it
                configTask()
            }
        }
    }

    private fun configTask(){
        newTask = false
        status = task.status
        binding.textToolbar.setText(R.string.text_toolbar_update_form_task_fragment)
        binding.editTextDescricao.setText(task.description)
        setStatus()
    }

    private fun setStatus(){
        val id = when (task.status){
            Status.TODO -> R.id.rbTodo
            Status.DOING -> R.id.rbDoing
            else -> R.id.rbDone
        }
    }

    private fun initListener(){
        binding.buttonSave.setOnClickListener{
            valideData()
        }

        binding.radioGroup.setOnCheckedChangeListener { _, id -> status =
            when(id){
                R.id.rbTodo -> Status.TODO
                R.id.rbDoing -> Status.DOING
                else -> Status.DONE
            }
        }
    }

    private fun valideData(){
        val description = binding.editTextDescricao.text.toString().trim()

        if (description.isNotBlank()){

            hideKeyboard()
            binding.progressBar.isVisible = true

            if (newTask) {
                task = Task()

            }

            task.description = description
            task.status = status

            saveTask()
        }else{
            showBottomSheet(message = getString(R.string.description_empty_form_task_fragment))
        }
    }

    private fun saveTask(){
        //valida autenticação
        val userId = FireBaseHelper.getIdUser()
        if (userId == null){
            showBottomSheet(message = getString(R.string.error_generic))
            return
        }

        //mostra loading
        binding.progressBar.isVisible = true

        FireBaseHelper.getDatabase()
            .child("task")
            .child(userId)
            .child(task.id)
            .setValue(task)
            .addOnCompleteListener { result ->

                //oculta loading em qualquer caso
                binding.progressBar.isVisible = false

                if (result.isSuccessful){
                    Toast.makeText(requireContext(), R.string.text_save_sucess_form_task_fragment,
                        Toast.LENGTH_SHORT).show()

                    if (newTask){
                        findNavController().popBackStack()
                    }else{
                        Toast.makeText(
                            requireContext(),
                            R.string.text_update_sucess_form_task_fragment,
                            Toast.LENGTH_SHORT
                        ).show()

                        viewModel.setUpdateTask(task)
                        binding.progressBar.isVisible=false
                    }
                } else{
                    binding.progressBar.isVisible = false
                    val errorMessage = result.exception?.message
                        ?: getString(R.string.error_generic)
                    showBottomSheet(message = errorMessage)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


