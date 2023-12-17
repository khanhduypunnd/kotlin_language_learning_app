package com.example.learnlanguage.dialog

import android.view.View
import com.example.learnlanguage.R
import com.example.learnlanguage.base.BaseDialog
import com.example.learnlanguage.databinding.DialogSimpleMessageBinding

class SimpleMessageDialog(var message: String, var onClick: () -> Unit = {}): BaseDialog<DialogSimpleMessageBinding>() {
    companion object {
        internal val TAG = SimpleMessageDialog::class.java.name
    }

    override fun initAfterViewCreated(rootView: View) {
        binding.btConfirm.setOnClickListener {
            dismiss()
            onClick.invoke()
        }
        binding.tvContent.text = message
    }

    override fun getLayoutId(): Int = R.layout.dialog_simple_message

    override fun marginHorizon(): Float = 24f


}