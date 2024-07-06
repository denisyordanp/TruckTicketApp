package com.denisyordanp.truckticketapp.core.di

import com.denisyordanp.truckticketapp.core.remote.api.RemoteApi
import com.denisyordanp.truckticketapp.core.remote.implementation.RemoteApiImpl
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideRemoteApi(
        firestore: FirebaseFirestore
    ): RemoteApi = RemoteApiImpl(firestore)
}