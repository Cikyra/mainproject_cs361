package com.example.mainproject_cs361.compose.features.home

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mainproject_cs361.compose.composables.DailySchedule
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HomeScreen(onNavigateToSchedule: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomeScreenContent(onNavigateToSchedule)
    }
}

@Composable
fun HomeScreenContent(onNavigateToSchedule: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)) {
        HorizontalDivider(
            Modifier,
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primaryContainer
        )
        Text(   text = "Class Schedule",
                style = TextStyle(
                    fontSize = 20.sp,
                )
        )
        HorizontalDivider(
            Modifier,
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.primaryContainer
        )

        val formatter = SimpleDateFormat("EEEE, MMMM dd", Locale.US)
        Text(
            text = formatter.format(Calendar.getInstance().time),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(   text = "Select a class to quickly check in",
            style = TextStyle(
                fontSize = 15.sp,
            )
        )

        var studentName by remember { mutableStateOf("") }
        DailySchedule(Calendar.getInstance().time, "Home", studentName)



        Button(onClick = {onNavigateToSchedule()},
            colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary)){
            Text(
                text = "View Full Class Schedule",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

    }



}
