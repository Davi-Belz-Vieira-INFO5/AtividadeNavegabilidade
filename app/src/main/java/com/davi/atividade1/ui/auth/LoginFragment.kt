package com.davi.atividade1.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.davi.atividade1.R
import com.davi.atividade1.databinding.FragmentLoginBinding
import com.davi.atividade1.ui.BaseFragment
import com.davi.atividade1.ui.util.showBottomSheet
import com.davi.atividade1.util.FireBaseHelper
import com.google.firebase.auth.FirebaseAuth

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListener()
    }

    private fun initListener() {

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.btnRecover.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_recoverAccountFragment)
        }
        binding.buttonLogin.setOnClickListener {
            validateData()
        }

    }

    private fun validateData() {
        val email = binding.editextEmail.text.toString().trim()
        val senha = binding.edittextSenha.text.toString().trim()
        if (email.isNotBlank()) {
            if (senha.isNotBlank()) {

                hideKeyboard()
                binding.progressBar.isVisible = true
                loginUser(email,senha)
            } else {
                showBottomSheet(message = getString(R.string.password_empty))
            }
        } else {
            showBottomSheet(message = getString(R.string.email_empty))
        }
    }

    private fun loginUser(email: String, password: String){
        try{
            FireBaseHelper.getAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        //conseguiu autenticar com sucesso
                        findNavController().navigate((R.id.action_global_homeFragment))
                    }else{
                        //ocorreu falha na autenticação
                        binding.progressBar.isVisible = false
                        showBottomSheet(message = getString(FireBaseHelper.validError(task.exception?.message.toString())))
                    }
                }
        }catch ( e: Exception){
            Toast.makeText(requireContext(),e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}