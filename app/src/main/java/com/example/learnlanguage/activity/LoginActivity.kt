package com.example.learnlanguage.activity


import com.example.learnlanguage.R
import com.example.learnlanguage.base.BaseActivity
import com.example.learnlanguage.databinding.ActivityLoginBinding
import com.example.learnlanguage.fragment.LoginFragment
import com.example.learnlanguage.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity: BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    companion object {
        private val TAG = LoginActivity::class.java.name
    }
    private val viewModel: LoginViewModel by viewModel()

    override fun getVM(): LoginViewModel = viewModel

    override fun initViews() {
        showFrg(TAG, LoginFragment.TAG, false)
    }

    override fun getLayoutId(): Int = R.layout.activity_login
}