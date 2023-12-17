package com.example.learnlanguage.model

data class User(
    val userName: String,
    val address: String,
    val phone: String,
    val imagePath: String="",
){
    constructor():this("","","","")
}