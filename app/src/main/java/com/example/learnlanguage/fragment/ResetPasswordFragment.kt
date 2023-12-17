package com.example.learnlanguage.fragment

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import com.example.learnlanguage.R
import com.example.learnlanguage.base.BaseFragment
import com.example.learnlanguage.databinding.FragmentResetpasswordBinding
import com.example.learnlanguage.utils.ViewUtils
import com.example.learnlanguage.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ResetPasswordFragment: BaseFragment<FragmentResetpasswordBinding>() {
    companion object {
        internal val TAG = ResetPasswordFragment::class.java.name
    }
    private val viewModel: LoginViewModel by sharedViewModel()

    override fun getVM(): ViewModel = viewModel

    override fun initViews() {

        binding.buttonSendResetMail.setOnClickListener {
            val email = binding.edEmailReset.text.toString()

            if (TextUtils.isEmpty(email)) {
                ViewUtils.showMessageDialog(requireContext(), parentFragmentManager,"Please fill in your email address")
                return@setOnClickListener
            }
            ViewUtils.showBlur(parentFragmentManager)
            viewModel.sendResetPassword(email, onSuccess = {
                ViewUtils.dismissBlur()
                ViewUtils.showMessageDialog(requireContext(), parentFragmentManager, "Password change email has been sent, please check your email")
                parentFragmentManager.popBackStack()
            }, onFail = {
                ViewUtils.dismissBlur()
                ViewUtils.showMessageDialog(parentFragmentManager, it)
            })
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_resetpassword
}