package com.example.learnlanguage.fragment

import android.text.TextUtils
import com.example.learnlanguage.R

import com.example.learnlanguage.base.BaseFragment
import com.example.learnlanguage.databinding.FragmentRegisterBinding
import com.example.learnlanguage.utils.ViewUtils
import com.example.learnlanguage.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RegisterFragment: BaseFragment<FragmentRegisterBinding>() {
    companion object {
        internal val TAG = RegisterFragment::class.java.name
    }
    private val viewModel: LoginViewModel by sharedViewModel()

    override fun getVM() = viewModel

    override fun initViews() {
        binding.tvLoginHere.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        binding.buttonRegister.setOnClickListener {
            val email = binding.edEmailRegister.text.toString()
            val password = binding.edPasswordRegister.text.toString()
            val rePassword = binding.edConfirmPasswordRegister.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(rePassword)) {
                ViewUtils.showMessageDialog(requireContext(), parentFragmentManager, "Fill in all required fields")
                return@setOnClickListener
            }
            if (!password.equals(rePassword)) {
                ViewUtils.showMessageDialog(requireContext(), parentFragmentManager, "Password confirmation does not match the password. Please check again")
                return@setOnClickListener
            }

            ViewUtils.showBlur(parentFragmentManager)
            viewModel.register(email, password, onSuccess = {
                ViewUtils.dismissBlur()
                ViewUtils.showMessageDialog(requireContext(), parentFragmentManager, "Registration successful. Please check your email for verification and continue using your account") {
                    parentFragmentManager.popBackStack()
                }
            }, onFail = {
                ViewUtils.dismissBlur()
                ViewUtils.showMessageDialog(parentFragmentManager, it)
            })
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_register
}