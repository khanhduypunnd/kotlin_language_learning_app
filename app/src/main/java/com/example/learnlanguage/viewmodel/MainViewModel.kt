package com.example.learnlanguage.viewmodel

import com.example.learnlanguage.base.BaseViewModel
import com.example.learnlanguage.model.Word

class MainViewModel : BaseViewModel() {
    val prevListWords: MutableList<Word> = mutableListOf()

}