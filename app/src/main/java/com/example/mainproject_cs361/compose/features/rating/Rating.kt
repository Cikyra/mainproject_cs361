package com.example.mainproject_cs361.compose.features.rating

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

@Composable
fun Rating(
    entityId: String,
    onDismiss: () -> Unit = {}
) {
    var rating by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    var showDialog by remember { mutableStateOf(true) }
    val context = LocalContext.current

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
                onDismiss()
            },
            title = { Text("Rate the Class") },
            text = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    for (i in 1..5) {
                        TextButton(
                            onClick = { rating = i }
                        ) {
                            Text(
                                text = if (i <= rating) "★" else "☆",
                                style = MaterialTheme.typography.headlineLarge,
                                color = if (i <= rating) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {
                            val success = postRating(rating, entityId)
                            if (success) {
                                Toast.makeText(context, "Rating submitted!", Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, "Failed to submit rating.", Toast.LENGTH_SHORT).show()
                            }
                            showDialog = false
                            onDismiss()
                        }
                    },
                    enabled = rating > 0
                ) {
                    Text("Submit")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    onDismiss()
                }) {
                    Text("Cancel")
                }
            }
        )
    }
}

private suspend fun postRating(rating: Int, entityId: String): Boolean = withContext(Dispatchers.IO) {
    try {
        // NOTE: 127.0.0.1 refers to the Android device.
        // If the service is on your computer and you are using an emulator, use 10.0.2.2 instead.
        val url = URL("http://10.0.2.2:8000/ratings")
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.doOutput = true
        conn.setRequestProperty("Content-Type", "application/json")
        
        // Simple JSON payload
        val json = "{\"rating\": $rating, \"app_id\": \"HomeTeam\", \"entity_id\": \"$entityId\", \"user_id\": \"User1\"}"
        
        conn.outputStream.use { os ->
            os.write(json.toByteArray())
        }
        
        val responseCode = conn.responseCode
        conn.disconnect()
        return@withContext responseCode == 200
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}
