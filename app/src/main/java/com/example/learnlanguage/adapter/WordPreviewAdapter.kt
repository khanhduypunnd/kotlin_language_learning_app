package com.example.learnlanguage.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.learnlanguage.databinding.ItemWordPreviewBinding
import com.example.learnlanguage.model.Word


class WordPreviewAdapter(private var wordList: List<Word>): RecyclerView.Adapter<WordPreviewAdapter.WordPreviewViewHolder>() {

    inner class WordPreviewViewHolder(private val binding: ItemWordPreviewBinding): ViewHolder(binding.root) {
        fun bind(word : Word) {
            binding.edtNameWord.setText(word.name)
            binding.edtMeaningWord.setText(word.meaning)
            binding.tvUpdate.setOnClickListener {
                val name = binding.edtNameWord.text.toString()
                val mean = binding.edtMeaningWord.text.toString()
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mean)) {
                    return@setOnClickListener
                }
                wordList.get(absoluteAdapterPosition).name = name
                wordList.get(absoluteAdapterPosition).meaning = mean
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordPreviewViewHolder {
        val binding = ItemWordPreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordPreviewViewHolder(binding)
    }

    override fun getItemCount(): Int = wordList.size

    override fun onBindViewHolder(holder: WordPreviewViewHolder, position: Int) {
        val word = wordList[position]
        holder.bind(word)
    }
}