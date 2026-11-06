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
import com.davi.atividade1.databinding.FragmentRegisterBinding
import com.davi.atividade1.ui.BaseFragment
import com.davi.atividade1.ui.util.initToolbar
import com.davi.atividade1.ui.util.showBottomSheet
import com.davi.atividade1.util.FireBaseHelper
import com.google.firebase.auth.FirebaseAuth

class RegisterFragment : BaseFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        return binding.root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)
        initListener()
    }

    private fun initListener(){
        binding.buttonRegister.setOnClickListener{
            valideData()
        }
    }

    private fun valideData(){
        val email = binding.editextEmail.text.toString().trim()
        val senha = binding.edittextSenha.text.toString().trim()

        if (email.isNotBlank()){
            if (senha.isNotBlank()){

                hideKeyboard()
                binding.progressBar.isVisible = false
                registerUser(email, senha)

                Toast.makeText(requireContext(), "Tudo OK!", Toast.LENGTH_SHORT).show()
            }else {
                showBottomSheet(message = getString(R.string.password_empty_register_fragment))
            }
        }else{
                showBottomSheet(message = getString(R.string.email_empty_register_fragment))
        }
    }

    private fun registerUser(email: String, password: String){

        try {
            val auth = FirebaseAuth.getInstance()

            FireBaseHelper.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        findNavController().navigate(R.id.action_global_homeFragment)
                    } else {
                        binding.progressBar.isVisible = false
                        showBottomSheet(message = getString(FireBaseHelper.validError(task.exception?.message.toString())))
                    }

                }
        }catch ( e: Exception){
            Toast.makeText(requireContext(), e.message.toString(), Toast.LENGTH_SHORT).show()
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}