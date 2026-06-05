package com.example.mainproject_cs361.data.repo.features.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val messaging: FirebaseMessaging = FirebaseMessaging.getInstance()

    private val _authState = MutableStateFlow(auth.currentUser)
    val authState: StateFlow<FirebaseUser?> = _authState.asStateFlow()

    init {
        auth.addAuthStateListener { firebaseAuth ->
            _authState.value = firebaseAuth.currentUser
        }
    }

    val currentUser: FirebaseUser?
        get() = auth.currentUser

    suspend fun getFcmToken(): String? {
        return try {
            messaging.token.await()
        } catch (e: Exception) {
            null
        }
    }

    suspend fun registerFcmToken(): Boolean = withContext(Dispatchers.IO) {
        val user = auth.currentUser ?: run {
            Log.e("AuthRepository", "Cannot register FCM token: No user logged in")
            return@withContext false
        }
        try {
            val fcmToken = messaging.token.await()
            val idToken = user.getIdToken(true).await().token ?: return@withContext false

            Log.d("AuthRepository", "Registering FCM token for user: ${user.uid}")
            val url = URL("http://10.0.2.2:3000/register-fcm-token")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/json")
            conn.setRequestProperty("Authorization", "Bearer $idToken")

            val json = "{\"fcmToken\":\"$fcmToken\"}"
            conn.outputStream.use { it.write(json.toByteArray()) }

            val responseCode = conn.responseCode
            Log.d("AuthRepository", "Admin service response code: $responseCode")
            conn.disconnect()
            responseCode == 200 || responseCode == 201
        } catch (e: Exception) {
            Log.e("AuthRepository", "Error registering FCM token", e)
            false
        }
    }

    suspend fun signInAnonymously(): FirebaseUser? {
        return try {
            val result = auth.signInAnonymously().await()
            result.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun signInWithEmail(email: String, password: String): FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun signOut() {
        auth.signOut()
    }
}
