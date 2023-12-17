package com.example.learnlanguage.activity

import android.content.Intent
import com.example.learnlanguage.R
import com.example.learnlanguage.base.BaseActivity
import com.example.learnlanguage.databinding.ActivityAddTopicBinding
import com.example.learnlanguage.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddTopicActivity : BaseActivity<ActivityAddTopicBinding, MainViewModel>() {
    companion object {
        private val TAG = AddTopicActivity::class.java.name
    }
    private val viewModel: MainViewModel by viewModel()
    override fun getVM(): MainViewModel = viewModel

    override fun initViews() {

        binding.tvAddFolderImport.setOnClickListener {
            startActivity(Intent(this@AddTopicActivity, AddTopicImportCSVActivity::class.java))
        }
        binding.tvAddFolderEnterWord.setOnClickListener {
            startActivity(Intent(this@AddTopicActivity, AddTopicEnterWordActivity::class.java))
        }
    }

    override fun getLayoutId() = R.layout.activity_add_topic
}