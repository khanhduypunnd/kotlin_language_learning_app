package com.example.learnlanguage.adapter

import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learnlanguage.R
import com.example.learnlanguage.database.AppDatabase

import com.example.learnlanguage.databinding.ItemTopicBinding
import com.example.learnlanguage.model.Topic



class TopicAdapter(
    private var topicList: List<Topic>,
    private val onItemClick: ((topic: Topic) -> Unit)? = null,
    private val onRemoveItem: ((topic: Topic) -> Unit)? = null

) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val binding = ItemTopicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopicViewHolder(binding)
    }

    override fun getItemCount(): Int = topicList.size

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val topic = topicList[position]
        holder.bind(topic)
    }

    fun setListTopic(listTopic: List<Topic>) {
        this.topicList = listTopic
        notifyDataSetChanged()
    }

    inner class TopicViewHolder(private val binding: ItemTopicBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Topic) {
            binding.tvNameTopic.text = data.name
            val wordCount = searchTotalByWordTopic(data.id)

            binding.tvTotalWord.text = wordCount.toString()
            when(data.mode) {
                0 -> binding.tvModeTopic.setImageResource(R.drawable.ic_private)
                1 -> binding.tvModeTopic.setImageResource(R.drawable.ic_public)
            }
            binding.lnItemTopic.setOnClickListener {
                onItemClick?.invoke(data)
            }
            binding.ivDelete.setOnClickListener {
                onRemoveItem?.invoke(data)
            }
        }
        fun searchTotalByWordTopic(key: Int): Int {
            var topicWordCount = 0 // Biến đếm số từ của chủ đề
            val whereClause = "topic_id = ?"
            val whereArgs = arrayOf(key.toString())
            val dbHelper = AppDatabase(itemView.context)
            val st: SQLiteDatabase = dbHelper.readableDatabase
            val rs = st.query("word", null, whereClause, whereArgs, null, null, null)
            while (rs != null && rs.moveToNext()) {
                topicWordCount++
            }
            return topicWordCount
        }
    }
}