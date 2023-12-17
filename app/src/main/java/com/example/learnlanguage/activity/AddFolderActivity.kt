package com.example.learnlanguage.activity


import com.example.learnlanguage.R
import com.example.learnlanguage.base.BaseActivity
import com.example.learnlanguage.database.AppDatabase
import com.example.learnlanguage.databinding.ActivityAddFolderBinding
import com.example.learnlanguage.model.Folder
import com.example.learnlanguage.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddFolderActivity : BaseActivity<ActivityAddFolderBinding, MainViewModel>() {
    private val sqlHelper: AppDatabase by inject()
    companion object {
        internal val TAG = AddFolderActivity::class.java.name
    }
    private val viewModel: MainViewModel by viewModel()

    override fun getVM(): MainViewModel = viewModel

    override fun initViews() {
        binding.tvConfirmAddFolder.setOnClickListener {
            sqlHelper.addFolder(Folder(binding.edNameFolderAddFolder.text.toString()))
            finish()
        }
    }

    override fun getLayoutId() = R.layout.activity_add_folder
}