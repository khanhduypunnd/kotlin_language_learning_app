package com.example.learnlanguage.activity

import android.content.Intent
import android.util.Log
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.learnlanguage.R
import com.example.learnlanguage.adapter.WordPreviewAdapter
import com.example.learnlanguage.base.BaseActivity
import com.example.learnlanguage.database.AppDatabase
import com.example.learnlanguage.databinding.ActivityAddTopicImportBinding
import com.example.learnlanguage.model.Topic
import com.example.learnlanguage.model.Word
import com.example.learnlanguage.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.BufferedReader
import java.io.InputStreamReader

class AddTopicImportCSVActivity: BaseActivity<ActivityAddTopicImportBinding, MainViewModel>() {
    private val sqlHelper: AppDatabase by inject()
    companion object {
        internal val TAG = SplashActivity::class.java.name
        internal val TOPIC_COLLECTION = "topic"
        internal val PICK_CSV_REQUEST_CODE = 1011
    }
    private val viewModel: MainViewModel by viewModel()
    private var modeTopic = 0
    private val firestore: FirebaseFirestore by inject()
    private val firebaseAuth: FirebaseAuth by inject()
    private lateinit var previewAdapter: WordPreviewAdapter

    override fun getVM(): MainViewModel = viewModel

    override fun initViews() {
        binding.rgModeImport.setOnCheckedChangeListener { _, i ->
            when (i) {
                binding.btPrivateImport.id -> modeTopic = 0
                binding.btPublicImport.id -> modeTopic = 1
                else -> modeTopic = 0
            }
        }
        binding.tvConfirmImport.setOnClickListener {
            val topic = Topic(binding.edtNameTopicImport.text.toString(),0, modeTopic)
            val topicId = sqlHelper.addTopic(topic, binding.spinnerFolderImport.selectedItem.toString().toInt())
            // Neu pre list khong rong tuc la dang import thi save cac tu dang import vao topic nay
            for (word in viewModel.prevListWords) {
                sqlHelper.addWord(word, topicId.toInt())

            }
            // Topic la public thi day len firebase
            if (topic.mode == 1) {
                val data = hashMapOf(
                    "name" to topic.name,
                    "total" to topic.total,
                    "id" to topic.id,
                    "userId" to firebaseAuth.uid
                )
                firestore.collection(TOPIC_COLLECTION).add(data).addOnCompleteListener {
                    //

                }
            }
            finish()
        }

        val list: ArrayList<String> = ArrayList()
        for (i in 0 until sqlHelper.getAllFolder().size) {
            list.add(sqlHelper.getAllFolder()[i].id.toString())
        }
        val arr = list.toTypedArray()
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, arr
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerFolderImport.adapter = adapter

        // Nut import file csv
        binding.tvImport.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "text/*"

            startActivityForResult(intent, PICK_CSV_REQUEST_CODE)
        }

        //
        previewAdapter = WordPreviewAdapter(viewModel.prevListWords)
        binding.rvPreviewImport.adapter = previewAdapter
        binding.rvPreviewImport.layoutManager = LinearLayoutManager(this)
    }

    override fun getLayoutId() = R.layout.activity_add_topic_import

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_CSV_REQUEST_CODE) {
                data?.data?.let {
                    val inputStream = contentResolver.openInputStream(it)
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        // Lấy dữ liệu từng cột trong dòng
                        val columns = line!!.split(",")
                        val word = Word(columns.get(0), columns.get(1), 0)
                        viewModel.prevListWords.add(word)

                        Log.d(TAG, "onActivityResult: $word")
                    }

                    previewAdapter.notifyDataSetChanged()
                    reader.close()
                }
            }
        }
    }
}