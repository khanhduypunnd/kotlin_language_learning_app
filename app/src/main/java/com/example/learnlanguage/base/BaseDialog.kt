package com.example.learnlanguage.base

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatDialog
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment

abstract class BaseDialog<BD: ViewDataBinding>: DialogFragment() {
    protected var rootView: View? = null
    protected var hasTitle = false
    protected lateinit var binding: BD

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = AppCompatDialog(requireContext())
        if (!hasTitle) {
            dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initData()
        initViews()
        initEvents()
        initObserve()
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAfterViewCreated(view)
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            val width = calculateWindowDialogWidth(marginHorizon())
            getDialog()?.window?.setLayout(
                width,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    protected fun initData() {}
    open fun initViews() {}
    protected fun initEvents() {}
    protected fun initObserve() {}

    abstract fun initAfterViewCreated(rootView: View)
    abstract fun getLayoutId(): Int
    abstract fun marginHorizon(): Float

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }


    protected fun calculateWindowDialogWidth(margin: Float): Int {
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels

        val marginPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            margin,
            resources.displayMetrics
        )

        return (screenWidth - (marginPx * 2)).toInt()
    }
}

