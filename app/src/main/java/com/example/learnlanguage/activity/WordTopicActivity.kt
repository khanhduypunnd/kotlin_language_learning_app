package com.example.learnlanguage.activity

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnlanguage.R
import com.example.learnlanguage.adapter.WordAdapter
import com.example.learnlanguage.base.BaseActivity
import com.example.learnlanguage.database.AppDatabase
import com.example.learnlanguage.databinding.ActivityTopicDetailsBinding
import com.example.learnlanguage.model.Topic
import com.example.learnlanguage.share.ItemDecoration
import com.example.learnlanguage.viewmodel.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class WordTopicActivity: BaseActivity<ActivityTopicDetailsBinding, MainViewModel>() {
    companion object {
        private val TAG = MainViewModel::class.java.name
        internal val TOPIC_WORD = "TOPIC_WORD"
        internal val TOPIC_DATA = "TOPIC_DATA"
        internal val TOPIC_ID = "TOPIC_ID"
    }
    private val sqlHelper: AppDatabase by inject()
    private lateinit var topicWordAdapter: WordAdapter
    private val viewModel: MainViewModel by viewModel()
    private var topicWordId = 0
    private lateinit var topic : Topic
    override fun getVM(): MainViewModel = viewModel

    override fun initViews() {
        topicWordId = intent.getIntExtra(TOPIC_WORD,0)
        topic = intent.getSerializableExtra(TOPIC_DATA) as Topic
        val listWordTopic = sqlHelper.searchByWordTopic(topicWordId)
        topicWordAdapter = WordAdapter(listWordTopic){

        }
        binding.rcvTopic.adapter = topicWordAdapter
        binding.rcvTopic.layoutManager = LinearLayoutManager(this)
        val spaceHeightInPixels = resources.getDimensionPixelSize(R.dimen.space_between_items)
        binding.rcvTopic.addItemDecoration(ItemDecoration(spaceHeightInPixels))
        binding.ivAddWordTopic.setOnClickListener {
            val intent = Intent(this@WordTopicActivity, AddWordTopicActivity::class.java)
            intent.putExtra(TOPIC_ID,topicWordId)
            intent.putExtra(TOPIC_DATA,topic)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val listWordTopic = sqlHelper.searchByWordTopic(topicWordId)
        topicWordAdapter.setListWordTopic(listWordTopic)
    }
    override fun getLayoutId() = R.layout.activity_topic_details
}