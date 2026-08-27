package com.davi.atividade1.ui.util

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity

fun Fragment.initToolbar(toolbar: Toolbar){
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title=""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}