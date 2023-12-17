package com.example.learnlanguage.utils

import kotlin.random.Random


class WordGenerator {
    fun generateRandomStrings(): List<String> {
        val strings = mutableListOf<String>()

        repeat(3) {
            val randomLength = Random.nextInt(5, 10) // Độ dài ngẫu nhiên từ 5 đến 10
            val randomString = (1..randomLength)
                .map { ('a'..'z').random() } // Chọn một ký tự ngẫu nhiên từ 'a' đến 'z'
                .joinToString("") // Kết hợp các ký tự thành chuỗi

            strings.add(randomString)
        }

        return strings
    }
}
