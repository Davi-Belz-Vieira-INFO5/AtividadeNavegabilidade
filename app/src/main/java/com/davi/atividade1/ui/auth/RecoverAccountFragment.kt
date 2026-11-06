package com.davi.atividade1.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.davi.atividade1.R
import com.davi.atividade1.databinding.FragmentRecoverAccountBinding
import com.davi.atividade1.ui.BaseFragment
import com.davi.atividade1.ui.util.initToolbar
import com.davi.atividade1.ui.util.showBottomSheet
import com.davi.atividade1.util.FireBaseHelper
import com.google.firebase.auth.FirebaseAuth

class RecoverAccountFragment : BaseFragment() {

    private var _binding: FragmentRecoverAccountBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRecoverAccountBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar(binding.toolbar)

        initListener()
    }

    private fun initListener(){
        binding.buttonEnviar.setOnClickListener{
            valideData()
        }
    }

    private fun valideData(){
        val email = binding.editextEmail.text.toString().trim()

        if (email.isNotBlank()){

            hideKeyboard()
            binding.progressBar.isVisible=true
            recoverAccountUser(email)
        }else{
            showBottomSheet(message = getString(R.string.email_empty))
        }
    }

    private fun recoverAccountUser(email: String){
        try {
            FireBaseHelper.getAuth().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    binding.progressBar.isVisible=false
                    if (task.isSuccessful){
                        showBottomSheet(message = getString(R.string.text_message_recover_account_fragment))
                    }else{
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