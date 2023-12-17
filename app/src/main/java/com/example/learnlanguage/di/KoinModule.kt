package com.example.learnlanguage.di

import android.content.Context
import android.location.LocationManager
import com.example.learnlanguage.database.AppDatabase
import com.example.learnlanguage.viewmodel.LoginViewModel
import com.example.learnlanguage.viewmodel.MainViewModel
import com.example.learnlanguage.viewmodel.ProfileViewModel
import com.example.learnlanguage.viewmodel.EditProfileViewModel
import com.example.learnlanguage.viewmodel.SplashViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val KoinModule = module {
        single { AppDatabase(androidContext()) }
        single { FirebaseAuth.getInstance() }
        single { Firebase.firestore }
        single { FirebaseStorage.getInstance().reference }
        factory { androidContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager }
        viewModel { MainViewModel() }
        viewModel { SplashViewModel() }
        viewModel { LoginViewModel() }
        viewModel { ProfileViewModel() }
        viewModel { EditProfileViewModel() }
}