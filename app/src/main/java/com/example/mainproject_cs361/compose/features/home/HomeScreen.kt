package com.example.mainproject_cs361.compose.features.home

import android.icu.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mainproject_cs361.compose.composables.DailySchedule
import com.example.mainproject_cs361.utils.MockClassRepository
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.mainproject_cs361.data.model.domain.Class

@Composable
fun HomeScreen(
    repository: MockClassRepository,
    onNavigateToSchedule: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        HomeScreenContent(repository, onNavigateToSchedule)
    }
}

@Composable
fun HomeScreenContent(
    repository: MockClassRepository,
    onNavigateToSchedule: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
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
        var currClass by remember { mutableStateOf<Class?>(null) }
        var showDialogue by remember { mutableStateOf(false) }
        var showCancel by remember { mutableStateOf(false) }

        DailySchedule(
            day = Calendar.getInstance().time,
            screen = "Home",
            repository = repository,
            onCheckInConfirm = { name, clickedClass ->
                studentName = name
                currClass = clickedClass
                showDialogue = true
            }
        )

        if(studentName != ""){
            if(repository.checkIn(studentName, currClass?.id ?: "")){
                Dialog(onDismissRequest = { studentName = "" }) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface)
                    ) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Check In Complete", style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(15.dp,10.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$studentName is now checked in for ",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center),
                            textAlign = TextAlign.Center,
                        )
                        " " + currClass?.startTime + " " + currClass?.title
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = " " + currClass?.startTime + " " + currClass?.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            else{ //already checked in
                if(showDialogue) {
                    AlertDialog(
                        onDismissRequest = { 
                            showDialogue = false
                            studentName = ""
                        },
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        title = { Text(text = "Check In") },
                        text = {
                            Column {
                                Text("$studentName is already checked in for: ")
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(currClass?.startTime + " " + currClass?.title)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Would you like to cancel your check in?")
                            }
                        },
                        confirmButton = {
                            TextButton(
                                onClick = { 
                                    showDialogue = false
                                    studentName = ""
                                },
                                colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.primary)
                            ) {
                                Text("No, return", color = MaterialTheme.colorScheme.onPrimary)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = {
                                showCancel = true
                                showDialogue = false
                            }) {
                                Text("Yes, cancel check in")
                            }
                        }
                    )
                }
                
                if(showCancel){
                    repository.classCheckIns.getValue(studentName).remove(currClass?.id)
                    Dialog(onDismissRequest = { 
                        showCancel = false
                        studentName = ""
                    }) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .padding(16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSurface)
                        ) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Check In Canceled", style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(15.dp,10.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "$studentName is no longer checked in",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentSize(Alignment.Center),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }


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
