package com.example.learnlanguage.fragment

import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnlanguage.R
import com.example.learnlanguage.activity.AddFolderActivity
import com.example.learnlanguage.activity.FolderDetailActivity
import com.example.learnlanguage.adapter.FolderAdapter
import com.example.learnlanguage.base.BaseFragment
import com.example.learnlanguage.database.AppDatabase
import com.example.learnlanguage.databinding.FragmentFolderBinding
import com.example.learnlanguage.share.ItemDecoration
import com.example.learnlanguage.viewmodel.MainViewModel

import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FolderFragment : BaseFragment<FragmentFolderBinding>() {
    private val sqlHelper: AppDatabase by inject()
    private lateinit var folderAdapter: FolderAdapter
    override fun getVM(): ViewModel = viewModel
    private val viewModel: MainViewModel by viewModel()
    override fun initViews() {
        val listFolder = sqlHelper.getAllFolder()
        folderAdapter = FolderAdapter(listFolder,{ folder ->
            val intent = Intent(activity, FolderDetailActivity::class.java)
            intent.putExtra(FolderDetailActivity.FOLDER_ID, folder.id)
            startActivity(intent)
        },{
            sqlHelper.deleteFolder(it.id)
            Toast.makeText(activity,"Folder has been removed",Toast.LENGTH_SHORT).show()
        })
        binding.rcvFolder.adapter = folderAdapter
        binding.rcvFolder.layoutManager = LinearLayoutManager(requireContext())
        val spaceHeightInPixels = resources.getDimensionPixelSize(R.dimen.space_between_items)
        binding.rcvFolder.addItemDecoration(ItemDecoration(spaceHeightInPixels))
        binding.ivAddSub.setOnClickListener {
            openAddFolderAct()
        }
    }

    override fun onResume() {
        super.onResume()
        val listFolder = sqlHelper.getAllFolder()
        folderAdapter.setListFolder(listFolder)
    }
    private fun openAddFolderAct() {
        startActivity(Intent(activity, AddFolderActivity::class.java ))
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_folder
    }
}
