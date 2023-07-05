package com.example.tonikarimovicapp.services

import com.example.tonikarimovicapp.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageServiceImpl
@Inject
constructor(private val firestore: FirebaseFirestore, private val auth: AccountService) :
    StorageService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val users: Flow<List<User>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                firestore.collection(USER_COLLECTION).whereNotEqualTo("id", user.id).dataObjects()
            }

    override suspend fun getUser(userId: String): User? =
        firestore.collection(USER_COLLECTION).document(userId).get().await().toObject()


    companion object {
        private const val USER_ID_FIELD = "userId"
        private const val USER_COLLECTION = "Users"
    }
}
