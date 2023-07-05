package com.example.tonikarimovicapp.services


import com.example.tonikarimovicapp.models.User
import kotlinx.coroutines.flow.Flow

interface StorageService {
  val users: Flow<List<User>>
  suspend fun getUser(userId: String): User?
}
