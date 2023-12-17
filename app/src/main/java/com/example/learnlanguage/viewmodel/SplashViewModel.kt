package com.example.learnlanguage.viewmodel


import com.example.learnlanguage.base.BaseViewModel
import com.example.learnlanguage.utils.SingleLiveEvent

class SplashViewModel : BaseViewModel() {
    val stateSplash =  SingleLiveEvent<Boolean>()
    init {
        stateSplash.value = true
    }
}