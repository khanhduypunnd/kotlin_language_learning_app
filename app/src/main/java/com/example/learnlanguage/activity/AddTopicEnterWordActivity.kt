package com.example.learnlanguage.activity


import android.widget.ArrayAdapter
import com.example.learnlanguage.R
import com.example.learnlanguage.base.BaseActivity
import com.example.learnlanguage.database.AppDatabase
import com.example.learnlanguage.databinding.ActivityAddTopicEnterWordBinding
import com.example.learnlanguage.model.Topic
import com.example.learnlanguage.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddTopicEnterWordActivity: BaseActivity<ActivityAddTopicEnterWordBinding, MainViewModel>() {
    private val sqlHelper: AppDatabase by inject()
    companion object {
        internal val TAG = SplashActivity::class.java.name
        internal val TOPIC_COLLECTION = "topic"

    }
    private val viewModel: MainViewModel by viewModel()
    private var modeTopic = 0
    private val firestore: FirebaseFirestore by inject()
    private val firebaseAuth: FirebaseAuth by inject()

    override fun getVM(): MainViewModel = viewModel

    override fun initViews() {
        binding.rgModeEnterWord.setOnCheckedChangeListener { _, i ->
            when (i) {
                binding.btPrivateEnterWord.id -> modeTopic = 0
                binding.btPublicEnterWord.id -> modeTopic = 1
                else -> modeTopic = 0
            }
        }
        binding.tvConfirmEnterWord.setOnClickListener {
            val topic = Topic(binding.edtNameTopicEnterWord.text.toString(),0, modeTopic)
            sqlHelper.addTopic(topic, binding.spinnerFolderEnterWord.selectedItem.toString().toInt())

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
        binding.spinnerFolderEnterWord.adapter = adapter


    }

    override fun getLayoutId() = R.layout.activity_add_topic_enter_word


}