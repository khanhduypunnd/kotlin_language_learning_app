package com.example.learnlanguage.activity

import com.example.learnlanguage.R
import com.example.learnlanguage.base.BaseActivity
import com.example.learnlanguage.databinding.ActivityMainBinding
import com.example.learnlanguage.fragment.HomeFragment
import com.example.learnlanguage.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    companion object {
        private val TAG = MainActivity::class.java.name
    }
    private val viewModel: MainViewModel by viewModel()
    override fun getVM(): MainViewModel = viewModel

    override fun initViews() {
        showFrg(TAG, HomeFragment.TAG, false)
    }

    override fun getLayoutId() = R.layout.activity_main
}