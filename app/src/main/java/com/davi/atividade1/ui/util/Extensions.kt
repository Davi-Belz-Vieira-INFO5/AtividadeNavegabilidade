package com.davi.atividade1.ui.util

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.davi.atividade1.R
import com.davi.atividade1.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.initToolbar(toolbar: Toolbar){
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title=""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener {
        activity?.onBackPressedDispatcher?.onBackPressed()
    }
}



fun Fragment.showBottomSheet(
    titleDialog: Int? = null,
    titleButton: Int? = null,
    message: Int,
    onClick: () -> Unit ={}
){
    val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
    val binding: BottomSheetBinding =
                BottomSheetBinding.inflate( layoutInflater, null, false)

    binding.textviewTitle.text= getText(titleDialog ?: R.string.text_title_warning )
    binding.textviewMessage.text = getText(message)
    binding.buttonOk.text = getText(titleButton ?: R.string.text_button_warning)
    binding.buttonOk.setOnClickListener {
        onClick()
        bottomSheetDialog.dismiss()
    }

    bottomSheetDialog.setContentView(binding.root)
    bottomSheetDialog.show()

}