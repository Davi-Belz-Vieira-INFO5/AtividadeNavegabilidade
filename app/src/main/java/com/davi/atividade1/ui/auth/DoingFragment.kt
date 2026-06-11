package com.davi.atividade1.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davi.atividade1.databinding.DoingFragmentBinding

class DoingFragment: Fragment() {
    private var _binding : DoingFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = DoingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

}