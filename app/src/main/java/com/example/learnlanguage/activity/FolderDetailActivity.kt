package com.example.learnlanguage.activity



import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnlanguage.R
import com.example.learnlanguage.adapter.TopicAdapter
import com.example.learnlanguage.base.BaseActivity
import com.example.learnlanguage.database.AppDatabase
import com.example.learnlanguage.databinding.ActivityFolderDetailsBinding
import com.example.learnlanguage.viewmodel.MainViewModel

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FolderDetailActivity : BaseActivity<ActivityFolderDetailsBinding, MainViewModel>() {
    private val sqlHelper: AppDatabase by inject()
    companion object {
        internal val FOLDER_ID = "FOLDER_ID"
        internal val TAG = MainViewModel::class.java.name
    }

    private lateinit var topicAdapter: TopicAdapter
    private var folderId: Int = 0
    private val viewModel: MainViewModel by viewModel()

    override fun getVM(): MainViewModel = viewModel

    override fun initViews() {
        folderId = intent.getIntExtra(FOLDER_ID,0)
        val listTopic = sqlHelper.searchByTopicByFolderId(folderId)
        listTopic.let {
            topicAdapter = TopicAdapter(it) { topic ->
                val intent = Intent(this@FolderDetailActivity, WordTopicActivity::class.java)
                intent.putExtra("TOPIC_WORD", topic)
                startActivity(intent)
            }
            binding.rcvTopicFolder.adapter = topicAdapter
            binding.rcvTopicFolder.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onResume() {
        super.onResume()
        val listTopic = sqlHelper.searchByTopicByFolderId(folderId)

        topicAdapter.setListTopic(listTopic)
    }
    override fun getLayoutId() = R.layout.activity_folder_details
}