package com.example.learnlanguage.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.example.learnlanguage.dialog.InProgressBlurDialog
import com.example.learnlanguage.dialog.SimpleMessageDialog


object ViewUtils {
    @SuppressLint("StaticFieldLeak")
    private var blurDialog: InProgressBlurDialog? = null

    fun showBlur(support: FragmentManager) {
        blurDialog = InProgressBlurDialog()
        blurDialog?.show(support, InProgressBlurDialog.TAG)
    }

    fun dismissBlur() {
        blurDialog?.dismiss()
    }

    fun showMessageDialog(context: Context, support: FragmentManager, msg: String, onCLick: () -> Unit = {}) {
        val str = msg
        val messageDialog = SimpleMessageDialog(str, onCLick)
        messageDialog.show(support, SimpleMessageDialog.TAG)
    }

    fun showMessageDialog(support: FragmentManager, msg: String, onCLick: () -> Unit = {}) {
        val messageDialog = SimpleMessageDialog(msg, onCLick)
        messageDialog.show(support, SimpleMessageDialog.TAG)
    }
}