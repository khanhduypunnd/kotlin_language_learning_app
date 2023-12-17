package com.example.learnlanguage.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.learnlanguage.databinding.ItemFolderBinding
import com.example.learnlanguage.model.Folder


class FolderAdapter(
    private var folderList: List<Folder>,
    private val onItemClick: ((folder: Folder) -> Unit)? = null,
    private val onRemoveItem: ((folder: Folder) -> Unit)? = null
) : RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val binding = ItemFolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FolderViewHolder(binding)
    }

    override fun getItemCount(): Int = folderList.size

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folder = folderList[position]
        holder.bind(folder)
    }

    fun setListFolder(listFolder: List<Folder>) {
        this.folderList = listFolder
        notifyDataSetChanged()
    }

    inner class FolderViewHolder(private val binding: ItemFolderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : Folder) {
            binding.tvNameFolder.text = data.name
            binding.lnItemFolder.setOnClickListener {
                onItemClick?.invoke(data)
            }
            binding.ivDelete.setOnClickListener {
                onRemoveItem?.invoke(data)
            }
        }
    }
}