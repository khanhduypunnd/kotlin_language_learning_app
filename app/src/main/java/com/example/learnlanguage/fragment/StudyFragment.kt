package com.example.learnlanguage.fragment



import android.content.Intent
import com.example.learnlanguage.R
import com.example.learnlanguage.activity.FillInWordActivity
import com.example.learnlanguage.activity.FlashCardActivity
import com.example.learnlanguage.activity.MultipleChoiceActivity
import com.example.learnlanguage.base.BaseFragment
import com.example.learnlanguage.databinding.FragmentStudyBinding
import com.example.learnlanguage.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class StudyFragment : BaseFragment<FragmentStudyBinding>() {
    companion object {
        private val TAG = StudyFragment::class.java.name
    }
    private val viewModel: MainViewModel by viewModel()
    override fun getVM(): MainViewModel = viewModel

   override fun initViews() {
       binding.tvFlashCard.setOnClickListener {
           startActivity(Intent(activity, FlashCardActivity::class.java))
       }
       binding.tvMultipleChoice.setOnClickListener {
           startActivity(Intent(activity, MultipleChoiceActivity::class.java))
       }
       binding.tvFillInWords.setOnClickListener {
           startActivity(Intent(activity, FillInWordActivity::class.java))
       }
   }

    override fun getLayoutId() = R.layout.fragment_study
}