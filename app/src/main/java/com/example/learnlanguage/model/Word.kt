package com.example.learnlanguage.model

import java.io.Serializable

class Word : Serializable {
    var id = 0
    var name: String = ""
    var meaning: String = ""
    var learState : Int = 0
    constructor(id: Int, name: String,meaning : String,learState : Int) {
        this.name = name
        this.meaning = meaning
        this.learState = learState
        this.id = id
    }
    constructor(name: String,meaning : String,learState : Int) {
        this.name = name
        this.meaning = meaning
        this.learState = learState
    }
}
