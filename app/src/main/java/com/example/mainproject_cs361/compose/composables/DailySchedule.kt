package com.example.mainproject_cs361.compose.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mainproject_cs361.R
import com.example.mainproject_cs361.compose.features.checkin.CheckIn
import com.example.mainproject_cs361.compose.features.register.Register
import com.example.mainproject_cs361.data.model.domain.Class
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailySchedule(
    day: Date?,
    screen: String,
    onCheckInConfirm: (String, Class) -> Unit = { _, _ -> }
) {
    var classes by remember { mutableStateOf(emptyList<Class>()) }
    var showCheckIn by remember { mutableStateOf(false) }
    var showRegistration by remember { mutableStateOf(false) }
    var clickedClass by remember { mutableStateOf<Class?>(null) }

    LaunchedEffect(day?.time) {
        classes = fetchClasses(day)
    }

    Column (
        modifier = Modifier.padding(2.dp)
    ){
        for (item in classes) {
            ListItem(
                headlineContent = { Text(item.title + ": " + item.startTime) },
                supportingContent = { item.description?.let { Text(it) } },
                trailingContent = {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.Center
                    ){
                        Icon(
                            painter = painterResource(R.drawable.arrow_right),
                            contentDescription = "select class"
                        )
                    }
                },
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier
                    .clickable {
                        if (screen == "Home") {
                            showCheckIn = true
                            clickedClass = item
                        } else if (screen == "Schedule") {
                            showRegistration = true
                            clickedClass = item
                        }
                    }
                    .padding(
                        horizontal = 10.dp,
                        vertical = 3.dp
                    )
                    .requiredHeight(90.dp)
            )
        }

        if(showCheckIn && clickedClass != null){
            CheckIn(
                clickedClass!!,
                day,
                closeDialogue = { showCheckIn = false },
                onConfirm = { name ->
                    onCheckInConfirm(name, clickedClass!!)
                    showCheckIn = false
                }
            )
        }
        if(showRegistration && clickedClass != null){
            Register(
                clickedClass!!,
                day,
                closeDialogue = { showRegistration = false },
                onConfirm = { name ->
                    onCheckInConfirm(name, clickedClass!!)
                    showRegistration = false
                }
            )
        }
    }
}

private val json = Json { ignoreUnknownKeys = true }

private suspend fun fetchClasses(day: Date?): List<Class> = withContext(Dispatchers.IO) {
    try {
        val formatter = SimpleDateFormat("EEEE", Locale.US)
        val dayString = formatter.format(day ?: Date())
        // Using 10.0.2.2 for emulator to access host's localhost
        val url = URL("http://10.0.2.2:8001/classes/day")
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "POST"
        conn.doOutput = true
        conn.setRequestProperty("Content-Type", "application/json")

        val jsonBody = "{\"day\": \"$dayString\"}"

        conn.outputStream.use { os ->
            os.write(jsonBody.toByteArray())
        }
        
        val responseText = conn.inputStream.bufferedReader().use { it.readText() }
        conn.disconnect()
        
        return@withContext json.decodeFromString<List<Class>>(responseText)
    } catch (e: Exception) {
        e.printStackTrace()
        emptyList()
    }
}

