package com.example.mainproject_cs361.utils

import com.example.mainproject_cs361.data.repo.features.schedule.ClassRepository
import java.util.Date
import java.util.Locale
import com.example.mainproject_cs361.data.model.domain.Class
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

class MockClassRepository : ClassRepository {
    private val baseUrl = "http://10.0.2.2:8001"

    suspend fun register(userId: String, classId: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("$baseUrl/registrations")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/json")

            val json = "{\"user_id\": \"$userId\", \"class_id\": $classId}"
            conn.outputStream.use { it.write(json.toByteArray()) }

            val responseCode = conn.responseCode
            conn.disconnect()
            return@withContext responseCode == 200 || responseCode == 201
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun cancelRegistration(userId: String, classId: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("$baseUrl/registrations/$userId/$classId")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "DELETE"

            val responseCode = conn.responseCode
            conn.disconnect()
            return@withContext responseCode == 200
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun checkIn(userId: String, classId: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("$baseUrl/checkins")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/json")

            val json = "{\"user_id\": \"$userId\", \"class_id\": $classId}"
            conn.outputStream.use { it.write(json.toByteArray()) }

            val responseCode = conn.responseCode
            conn.disconnect()
            return@withContext responseCode == 200 || responseCode == 201
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun cancelCheckIn(userId: String, classId: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("$baseUrl/checkins/$userId/$classId")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "DELETE"

            val responseCode = conn.responseCode
            conn.disconnect()
            return@withContext responseCode == 200
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun sendReminder(userId: String, studentName: String, classTitle: String, startTime: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("http://10.0.2.2:3001/sendNotification")
            val conn = url.openConnection() as HttpURLConnection
            conn.requestMethod = "POST"
            conn.doOutput = true
            conn.setRequestProperty("Content-Type", "application/json")

            val message = "$studentName has $classTitle class at $startTime."
            val json = """
                {
                  "targetUid": "$userId",
                  "notification": {
                    "title": "Reminder",
                    "body": "$message"
                  },
                  "data": {
                    "reminderId": "abc123"
                  }
                }
            """.trimIndent()

            conn.outputStream.use { it.write(json.toByteArray()) }

            val responseCode = conn.responseCode
            conn.disconnect()
            return@withContext responseCode == 200 || responseCode == 201
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun getClasses(day: Date?): MutableList<Class> {
        //By product of previous version of app. Will need to refactor this file/ClassRepository to reflect the new update
        return mutableListOf()
    }
}
