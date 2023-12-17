package com.example.learnlanguage.activity

import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.learnlanguage.R
import com.example.learnlanguage.base.BaseActivity
import com.example.learnlanguage.databinding.ActivitySplashBinding
import com.example.learnlanguage.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    companion object {
        internal val TAG = SplashActivity::class.java.name
    }
    private val viewModel: SplashViewModel by viewModel()

    override fun getVM(): SplashViewModel = viewModel

    override fun initViews() {
        viewModel.stateSplash.observe(this){
            if(it){
                Handler(Looper.getMainLooper()).postDelayed({
                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }, 2000)
            }
        }
    }

    override fun getLayoutId() = R.layout.activity_splash
}