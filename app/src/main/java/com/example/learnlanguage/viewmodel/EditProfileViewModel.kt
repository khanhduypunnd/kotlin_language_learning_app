package com.example.learnlanguage.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.viewModelScope
import com.example.learnlanguage.MainApplication
import com.example.learnlanguage.base.BaseViewModel
import com.example.learnlanguage.model.User
import com.example.learnlanguage.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

class EditProfileViewModel : BaseViewModel()  {
    private val firestore: FirebaseFirestore = Firebase.firestore
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: StorageReference = FirebaseStorage.getInstance().reference


    private val _user = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val user = _user.asStateFlow()

    private val _updateInfo = MutableStateFlow<Resource<User>>(Resource.Unspecified())
    val updateInfo = _updateInfo.asStateFlow()

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            _user.emit(Resource.Loading())
        }
        firestore.collection("user").document(auth.uid!!).get()
            .addOnSuccessListener {
                val user = it.toObject(User::class.java)
                user?.let {
                    viewModelScope.launch {
                        _user.emit(Resource.Success(it))
                    }
                }
            }.addOnFailureListener {
                viewModelScope.launch {
                    _user.emit(Resource.Error(it.message.toString()))
                }
            }
    }

    fun updateUser(user: User, imageUri: Uri?) {
        val areInputsvalid = user.userName.trim().isNotEmpty()
                && user.address.trim().isNotEmpty()
                && user.phone.trim().isNotEmpty()

        if (!areInputsvalid) {
            viewModelScope.launch {
                _user.emit(Resource.Error("Check your information again"))
            }
            return
        }

        viewModelScope.launch {
            _updateInfo.emit(Resource.Loading())
        }

        if (imageUri == null) {
            saveUserInformation(user, true)
        } else {
            saveUserInformationWithNewImage(user, imageUri)
        }

    }

    private fun saveUserInformationWithNewImage(user: User, imageUri: Uri) {
        viewModelScope.launch {
            try {
                val imageBitmap = MediaStore.Images.Media.getBitmap(
                    MainApplication.getInstance().contentResolver,
                    imageUri
                )
                val byteArrayOutputStream= ByteArrayOutputStream()
                imageBitmap.compress(Bitmap.CompressFormat.JPEG,96,byteArrayOutputStream)
                val imageByteArray=byteArrayOutputStream.toByteArray()
                val imageDirectory=
                    storage.child("profileImages/${auth.uid}/${UUID.randomUUID()}")
                val result=imageDirectory.putBytes(imageByteArray).await()
                val imageUrl=result.storage.downloadUrl.await().toString()
                saveUserInformation(user.copy(imagePath = imageUrl),false)
            } catch (e: java.lang.Exception) {
                viewModelScope.launch {
                    _updateInfo.emit(Resource.Error(e.message.toString()))
                }
            }
        }
    }

    private fun saveUserInformation(user: User, shouldRetrievedOldImage: Boolean) {
        firestore.runTransaction { transition ->
            val documentRef = firestore.collection("user").document(auth.uid!!)
            if (shouldRetrievedOldImage) {
                val currentUser = transition.get(documentRef).toObject(User::class.java)
                val newUser = user.copy(imagePath = currentUser?.imagePath ?: "")
                transition.set(documentRef, newUser)
            } else {
                transition.set(documentRef, user)
            }
        }.addOnSuccessListener {
            viewModelScope.launch {
                _updateInfo.emit(Resource.Success(user))
            }
        }.addOnFailureListener {
            viewModelScope.launch {
                _updateInfo.emit(Resource.Error(it.message.toString()))
            }
        }

    }

}