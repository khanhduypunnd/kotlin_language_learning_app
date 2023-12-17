package com.example.learnlanguage.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import com.example.learnlanguage.callback.OnActionCallBack

abstract class BaseFragment<K : ViewDataBinding> : Fragment() {
    lateinit var mRootView: View

    protected lateinit var binding: K

    var callBack: OnActionCallBack? = null

    var mData: Any? = null
    protected abstract fun getVM(): ViewModel

    companion object {
        const val SYS_ERROR: String = "An error occurred!"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    fun showNotify(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun showNotify(text: Int) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    abstract fun initViews()

    protected abstract fun getLayoutId(): Int
}