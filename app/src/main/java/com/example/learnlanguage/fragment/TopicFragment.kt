package com.example.learnlanguage.fragment

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnlanguage.R
import com.example.learnlanguage.activity.AddTopicActivity
import com.example.learnlanguage.activity.AddTopicImportCSVActivity
import com.example.learnlanguage.activity.WordTopicActivity
import com.example.learnlanguage.adapter.TopicAdapter

import com.example.learnlanguage.base.BaseFragment
import com.example.learnlanguage.database.AppDatabase
import com.example.learnlanguage.databinding.FragmentTopicBinding
import com.example.learnlanguage.share.ItemDecoration
import org.koin.android.ext.android.inject

class TopicFragment : BaseFragment<FragmentTopicBinding>() {
    private lateinit var topicAdapter: TopicAdapter
    private val sqlHelper: AppDatabase by inject()
    override fun getVM(): ViewModel {
        TODO("Not yet implemented")
    }

    override fun initViews() {
        val listTopic = sqlHelper.getAllTopic()
        listTopic.let {
            topicAdapter = TopicAdapter(it,{ topic ->
                val intent = Intent(activity, WordTopicActivity::class.java)
                intent.putExtra(WordTopicActivity.TOPIC_WORD, topic.id)
                intent.putExtra(WordTopicActivity.TOPIC_DATA, topic)
                startActivity(intent)
            }, {
                sqlHelper.deleteTopic(it.id)
                sqlHelper.deleteWord(it.id)
                Toast.makeText(activity,"Topic has been removed", Toast.LENGTH_SHORT).show()
            })
            binding.rcvTopic.adapter = topicAdapter
            binding.rcvTopic.layoutManager = LinearLayoutManager(activity)
            val spaceHeightInPixels = resources.getDimensionPixelSize(R.dimen.space_between_items)
            binding.rcvTopic.addItemDecoration(ItemDecoration(spaceHeightInPixels))
        }
        binding.ivAddTopic.setOnClickListener {
            val intent = Intent(activity, AddTopicActivity::class.java)
            startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_topic
    }
    override fun onResume() {
        super.onResume()
        val listTopic = sqlHelper.getAllTopic()
        topicAdapter.setListTopic(listTopic)
    }
}
