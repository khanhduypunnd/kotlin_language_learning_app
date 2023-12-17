package com.example.learnlanguage.model

import java.io.Serializable

class Folder : Serializable {
    var id = 0
    var name: String? = null
    constructor(id: Int, name: String) {
        this.name = name
        this.id = id
    }
    constructor(name: String) {
        this.name = name
    }
}
