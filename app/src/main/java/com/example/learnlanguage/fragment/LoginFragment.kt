package com.example.learnlanguage.fragment

import android.content.Intent
import android.text.TextUtils
import com.example.learnlanguage.R
import com.example.learnlanguage.activity.MainActivity
import com.example.learnlanguage.base.BaseFragment
import com.example.learnlanguage.databinding.FragmentLoginBinding
import com.example.learnlanguage.share.FragmentTransactionAnim
import com.example.learnlanguage.utils.ViewUtils
import com.example.learnlanguage.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class LoginFragment: BaseFragment<FragmentLoginBinding>(){
    companion object {
        internal val TAG = LoginFragment::class.java.name
    }
    private val viewModel: LoginViewModel by sharedViewModel()

    override fun getVM() = viewModel

    override fun initViews() {
        binding.tvRegisterhere.setOnClickListener {
            val transAnim = FragmentTransactionAnim().apply {
                this.enter = R.anim.in_screen_right_to_left
                this.exit = R.anim.out_screen_right_to_left
                this.popEnter = R.anim.in_screen_left_to_right
                this.popExit = R.anim.out_screen_left_to_right
            }
            callBack?.showFrg(TAG, null, RegisterFragment.TAG, true, transAnim)
        }
        binding.buttonLogin.setOnClickListener {
            val email = binding.edEmailLogin.text.toString()
            val password = binding.edPasswordLogin.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                ViewUtils.showMessageDialog(requireContext(), parentFragmentManager, "Fill in all required fields")
                return@setOnClickListener
            }

            ViewUtils.showBlur(parentFragmentManager)
            viewModel.login(email, password, onSuccess = {
                ViewUtils.dismissBlur()
                if (!viewModel.isUserEmailVerified()) {
                    ViewUtils.showMessageDialog(requireContext(), parentFragmentManager, "Email not verified, please check again")
                } else {
                    goToMainScreen()
                }
            }, onFail = {
                ViewUtils.dismissBlur()
                ViewUtils.showMessageDialog(parentFragmentManager, it)
            })
        }
        binding.tvForgetPassword.setOnClickListener {
            val transAnim = FragmentTransactionAnim().apply {
                this.enter = R.anim.in_screen_right_to_left
                this.exit = R.anim.out_screen_right_to_left
                this.popEnter = R.anim.in_screen_left_to_right
                this.popExit = R.anim.out_screen_left_to_right
            }
            callBack?.showFrg(ProfileFragment.TAG, null, ResetPasswordFragment.TAG, true, transAnim)
        }

    }

    override fun getLayoutId(): Int = R.layout.fragment_login

    private fun goToMainScreen() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}