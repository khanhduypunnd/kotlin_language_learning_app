package com.example.learnlanguage.firebase

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

object FirebaseAuthenticationManager {
    private var auth: FirebaseAuth = Firebase.auth

    fun signUpWithEmailAndPassword(email: String,
                                   password: String,
                                   onSuccess: () -> Unit,
                                   onFail: (String) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                sendVerificationMail(onSuccess)
            } else {
                it.exception?.let { exception ->
                    val message = exception.message ?: "Lỗi không xác định"
                    onFail.invoke(message)
                }
            }
        }
    }

    fun signInWithEmailAndPassword(email: String,
                                   password: String,
                                   onSuccess: () -> Unit = {},
                                   onFail: (String) -> Unit = {}) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess.invoke()
            } else {
                it.exception?.let { exception ->
                    val message = exception.message ?: "Lỗi không xác định"
                    onFail.invoke(message)
                }
            }
        }
    }

    fun isMailVerified(): Boolean = auth.currentUser?.isEmailVerified ?: false

    fun sendResetMail(email: String, onSuccess: () -> Unit = {}, onFail: (String) -> Unit = {}) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess.invoke()
            } else {
                it.exception?.let { exception ->
                    val message = exception.message ?: "Lỗi không xác định"
                    onFail.invoke(message)
                }
            }
        }
    }

    private fun sendVerificationMail(onSuccess: () -> Unit = {}) {
        val user = auth.currentUser

        user?.sendEmailVerification()?.addOnCompleteListener {
            if (it.isSuccessful) {
                onSuccess.invoke()
            }
        }
    }
}