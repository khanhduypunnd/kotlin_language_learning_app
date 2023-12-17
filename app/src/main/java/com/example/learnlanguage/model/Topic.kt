package com.example.learnlanguage.model

import java.io.Serializable
class Topic : Serializable {
    var id = 0
    var name: String = ""
    var total: Int = 0
    var mode : Int = 0
    constructor(id: Int, name: String,total : Int,mode : Int) {
        this.name = name
        this.total = total
        this.mode = mode
        this.id = id
    }
    constructor(name: String,total : Int,mode : Int) {
        this.name = name
        this.total = total
        this.mode = mode
    }
}
