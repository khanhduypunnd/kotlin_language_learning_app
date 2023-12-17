package com.example.learnlanguage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learnlanguage.databinding.ItemWordBinding
import com.example.learnlanguage.model.Word


class WordAdapter(
    private var wordList: List<Word>,
    private val onItemClick: (folder: Word) -> Unit
) : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun getItemCount(): Int = wordList.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = wordList[position]
        holder.bind(word)
    }

    fun setListWordTopic(listWordTopic: List<Word>) {
        this.wordList = listWordTopic
        notifyDataSetChanged()
    }

    inner class WordViewHolder(private val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word : Word) {
            binding.tvNameWord.text = word.name
            val learState = word.learState

            if (learState == 0) {
                binding.tvState.text = "Not learned"
            } else if (learState in 1..4) {
                binding.tvState.text = "Learned"
            } else {
                binding.tvState.text = "Memorized"
            }
            binding.lnItemWord.setOnClickListener {
                onItemClick.invoke(word)
            }
        }
    }
}