package com.example.learnlanguage.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentTransaction
import com.example.learnlanguage.callback.OnActionCallBack
import com.example.learnlanguage.R
import com.example.learnlanguage.share.FragmentTransactionAnim

abstract class BaseActivity<BD: ViewDataBinding, VM: BaseViewModel>: AppCompatActivity(),
    OnActionCallBack {
    protected lateinit var binding: BD
    private lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        mViewModel = getVM()
        initViews()
    }

    override fun showFrg(backTag: String, data: Any?, tag: String, isBacked: Boolean, anim: FragmentTransactionAnim?) {
        try {
            val clazz = Class.forName(tag)
            val constructor = clazz.getConstructor()
            val frg = constructor.newInstance() as BaseFragment<*>

            frg.callBack = this
            frg.mData = data

            val trans: FragmentTransaction = supportFragmentManager.beginTransaction()
            anim?.let {
                trans.setCustomAnimations(it.enter, it.exit, it.popEnter, it.popExit)
            }
            if (isBacked) {
                trans.addToBackStack(backTag)
            }
            trans.replace(R.id.act_main, frg).commit()
        } catch (e: Exception) {
            showNotify("Err: " + e.message)
            e.printStackTrace()
        }
    }

    override fun showFrg(backTag: String, tag: String, isBacked: Boolean) {
        showFrg(backTag, null, tag, isBacked,null)
    }

    override fun closeApp() {
        finish()
    }

    protected fun showNotify(sms: String) {
        Toast.makeText(this, sms, Toast.LENGTH_SHORT).show()
    }

    abstract fun getVM(): VM

    abstract fun initViews()

    abstract fun getLayoutId(): Int
}