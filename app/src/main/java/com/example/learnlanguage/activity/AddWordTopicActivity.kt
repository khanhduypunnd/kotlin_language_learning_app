package com.example.learnlanguage.activity


import com.example.learnlanguage.R
import com.example.learnlanguage.base.BaseActivity
import com.example.learnlanguage.database.AppDatabase
import com.example.learnlanguage.databinding.ActivityAddWordBinding
import com.example.learnlanguage.model.Topic
import com.example.learnlanguage.model.Word
import com.example.learnlanguage.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddWordTopicActivity: BaseActivity<ActivityAddWordBinding, MainViewModel>() {
    private val sqlHelper: AppDatabase by inject()
    private lateinit var topic : Topic
    companion object {
        internal val TAG = AddWordTopicActivity::class.java.name
    }
    private val viewModel: MainViewModel by viewModel()

    override fun getVM(): MainViewModel = viewModel

    override fun initViews() {
        topic = intent.getSerializableExtra(WordTopicActivity.TOPIC_DATA) as Topic
        binding.tvConfirmAddWord.setOnClickListener {
            sqlHelper.addWord(
                Word(binding.edtNameWord.text.toString(),binding.edtMeaningWord.text.toString(),
                0),id = intent.getIntExtra(WordTopicActivity.TOPIC_ID,0))
            val index = ++topic.total
            sqlHelper.updateTopic(Topic(topic.id,topic.name,index, topic.mode))
            finish()
        }

    }

    override fun getLayoutId() = R.layout.activity_add_word
}