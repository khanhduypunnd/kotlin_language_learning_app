package com.example.learnlanguage.activity

import android.widget.Toast
import com.example.learnlanguage.R
import com.example.learnlanguage.base.BaseActivity
import com.example.learnlanguage.database.AppDatabase
import com.example.learnlanguage.databinding.ActivityMultipleChoiceBinding
import com.example.learnlanguage.model.Word
import com.example.learnlanguage.utils.WordGenerator
import com.example.learnlanguage.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MultipleChoiceActivity : BaseActivity<ActivityMultipleChoiceBinding, MainViewModel>() {
    private lateinit var listData: ArrayList<String>
    private lateinit var listResult: MutableList<String>
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
        listData= WordGenerator().generateRandomStrings() as ArrayList
        listData.add(listWord[index].meaning)
        listResult = listData.toMutableList()
        listResult.shuffle()
        binding.tvWordA.text = listResult[0]
        binding.tvWordB.text = listResult[1]
        binding.tvWordC.text = listResult[2]
        binding.tvWordD.text = listResult[3]
        binding.tvWordA.setOnClickListener {
            checkIsSuccessAns(binding.tvWordA.text.toString())
            index++
            reloadData()
        }
        binding.tvWordB.setOnClickListener {
            checkIsSuccessAns(binding.tvWordB.text.toString())
            index++
            reloadData()
        }
        binding.tvWordC.setOnClickListener {
            checkIsSuccessAns(binding.tvWordC.text.toString())
            index++
            reloadData()
        }
        binding.tvWordD.setOnClickListener {
            checkIsSuccessAns(binding.tvWordD.text.toString())
            index++
            reloadData()
        }
    }

    private fun checkIsSuccessAns(ansString : String) {
        var stateWord = 0
        if (listWord[index].meaning == ansString){
            if (listWord[index].learState < 100) {
                stateWord = ++listWord[index].learState
            }
            sqlHelper.updateWord(Word(listWord[index].id,listWord[index].name,listWord[index].meaning, stateWord))
            Toast.makeText(this@MultipleChoiceActivity, "Correct", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MultipleChoiceActivity, "Incorrect", Toast.LENGTH_SHORT).show()
        }
    }

    private fun reloadData() {
        if (index >= listWord.size) {
            Toast.makeText(this@MultipleChoiceActivity,"Out of words", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            binding.tvWord.text = listWord[index].name
            listData.clear()
            listData= WordGenerator().generateRandomStrings() as ArrayList
            listData.add(listWord[index].meaning)
            listResult = listData.toMutableList()
            listResult.shuffle()
            binding.tvWordA.text = listResult[0]
            binding.tvWordB.text = listResult[1]
            binding.tvWordC.text = listResult[2]
            binding.tvWordD.text = listResult[3]
        }
    }

    override fun getLayoutId() = R.layout.activity_multiple_choice
}