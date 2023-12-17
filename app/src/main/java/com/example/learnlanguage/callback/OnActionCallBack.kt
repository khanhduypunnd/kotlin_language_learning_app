package com.example.learnlanguage.callback

import com.example.learnlanguage.share.FragmentTransactionAnim


interface OnActionCallBack {
    fun showFrg(backTag : String, tag : String, isBacked : Boolean)
    fun showFrg(backTag : String, data  : Any?, tag : String, isBacked : Boolean, anim: FragmentTransactionAnim?)
    fun closeApp()
}