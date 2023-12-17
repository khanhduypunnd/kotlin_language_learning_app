package com.example.learnlanguage.activity


import android.widget.Toast
import com.example.learnlanguage.R
import com.example.learnlanguage.base.BaseActivity
import com.example.learnlanguage.database.AppDatabase
import com.example.learnlanguage.databinding.ActivityFillWordBinding
import com.example.learnlanguage.model.Word
import com.example.learnlanguage.viewmodel.MainViewModel

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FillInWordActivity : BaseActivity<ActivityFillWordBinding, MainViewModel>() {
    private lateinit var listWord: List<Word>
    private val sqlHelper: AppDatabase by inject()
    private var index = 0
    companion object {
        private val TAG = MainActivity::class.java.name
    }
    private val viewModel: MainViewModel by viewModel()
    override fun getVM(): MainViewModel = viewModel

    override fun initViews() {
        listWord = sqlHelper.getAllWordTopic()
        binding.tvWord.text = listWord[index].name
        binding.tvOk.setOnClickListener {
            if (listWord[index].meaning == binding.tvWordMean.text.toString()) {
                var stateWord = 0
                if (listWord[index].learState < 100) {
                    stateWord = ++listWord[index].learState
                }
                sqlHelper.updateWord(Word(listWord[index].id,listWord[index].name,listWord[index].meaning, stateWord))
                Toast.makeText(this@FillInWordActivity, "Correct", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(this@FillInWordActivity, "Incorrect", Toast.LENGTH_SHORT).show()
            index++
            reloadData()
        }
    }

    private fun reloadData() {
        if (index >= listWord.size) {
            Toast.makeText(this@FillInWordActivity,"Out of words", Toast.LENGTH_SHORT).show()
            finish()
        }
        else binding.tvWord.text = listWord[index].name
    }

    override fun getLayoutId() = R.layout.activity_fill_word
}