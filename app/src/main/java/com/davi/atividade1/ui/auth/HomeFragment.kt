package com.davi.atividade1.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.davi.atividade1.R
import com.davi.atividade1.databinding.FragmentHomeBinding
import com.davi.atividade1.ui.adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabs()
    }
    private fun initTabs(){
        val pageAdapter = ViewPagerAdapter(requireActivity())
        binding.viewPager.adapter = pageAdapter
        pageAdapter.addFragment(TodoFragment(), R.string.status_task_todo)
        pageAdapter.addFragment(TodoFragment(), R.string.status_task_doing)
        pageAdapter.addFragment(TodoFragment(), R.string.status_task_done)

        binding.viewPager.offscreenPageLimit=pageAdapter.itemCount

        TabLayoutMediator(binding.tabs, binding.viewPager){ tab, position ->
            tab.text = getString(pageAdapter.getTittle(position))

        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}