package com.example.learnlanguage.activity

import android.widget.Toast
import com.example.learnlanguage.R
import com.example.learnlanguage.base.BaseActivity
import com.example.learnlanguage.database.AppDatabase
import com.example.learnlanguage.databinding.ActivityFlashCardBinding
import com.example.learnlanguage.model.Word
import com.example.learnlanguage.utils.WordGenerator
import com.example.learnlanguage.viewmodel.MainViewModel

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FlashCardActivity : BaseActivity<ActivityFlashCardBinding, MainViewModel>() {
    private lateinit var listData: ArrayList<String>
    private lateinit var listWord: List<Word>
    private lateinit var listResult: MutableList<String>
    private val sqlHelper: AppDatabase by inject()
    private var index = 0
    private var trueText = ""
    private var selectedText = ""
    private var isSelected = false
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
        binding.tvWord.text = listWord[index].name
        trueText = listWord[index].meaning
        binding.flipTextView1.getTextBackData(listResult[0])
        binding.flipTextView2.getTextBackData(listResult[1])
        binding.flipTextView3.getTextBackData(listResult[2])
        binding.flipTextView4.getTextBackData(listResult[3])
        setupClickListeners()
        binding.tvOk.setOnClickListener {
            if (trueText == selectedText) {
                var stateWord = 0
                if (listWord[index].learState < 100) {
                    stateWord = ++listWord[index].learState
                }
                sqlHelper.updateWord(Word(listWord[index].id,listWord[index].name,listWord[index].meaning, stateWord))
                Toast.makeText(this@FlashCardActivity, "Correct", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@FlashCardActivity, "Incorrect", Toast.LENGTH_SHORT).show()
            }
            index++
            reloadData()
        }
    }
    private fun reloadData() {
        if (index >= listWord.size) {
            Toast.makeText(this@FlashCardActivity,"Out of words", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            binding.tvWord.text = listWord[index].name
            val flipViews = listOf(binding.flipTextView1, binding.flipTextView2, binding.flipTextView3, binding.flipTextView4)
            flipViews.forEachIndexed { index, flipView ->
                flipViews.filter { it != flipView && it.isFlipped }.forEach {
                    it.flip()
                }
            }
            listData.clear()
            listData= WordGenerator().generateRandomStrings() as ArrayList
            listData.add(listWord[index].meaning)
            listResult = listData.toMutableList()
            listResult.shuffle()
            binding.flipTextView1.getTextBackData(listResult[0])
            binding.flipTextView2.getTextBackData(listResult[1])
            binding.flipTextView3.getTextBackData(listResult[2])
            binding.flipTextView4.getTextBackData(listResult[3])
            trueText = listWord[index].meaning
        }
    }
    private fun setupClickListeners() {
        val flipViews = listOf(binding.flipTextView1, binding.flipTextView2, binding.flipTextView3, binding.flipTextView4)
        flipViews.forEachIndexed { index, flipView ->
            flipView.setOnClickListener {
                if (!flipView.isFlipped) {
                    // Nếu có bất kỳ FlipView nào khác đang lật, chuyển chúng về trạng thái không lật
                    flipViews.filter { it != flipView && it.isFlipped }.forEach {
                        it.flip()
                    }

                    isSelected = !isSelected
                    if (isSelected) {
                        selectedText = listResult[index]
                        flipView.flip()
                    }
                }
            }
        }
    }

    override fun getLayoutId() = R.layout.activity_flash_card
}