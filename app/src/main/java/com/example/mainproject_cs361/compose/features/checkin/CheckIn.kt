package com.example.mainproject_cs361.compose.features.checkin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.mainproject_cs361.data.model.domain.Class
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CheckIn(curr : Class, day : Date?, closeDialogue : () -> Unit, onConfirm: (String) -> Unit){

    var studentName by remember { mutableStateOf("") }
    val simpleDateFormat = SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.US)
    val dateTime = simpleDateFormat.format(day?.time).toString()

    AlertDialog(
        onDismissRequest = closeDialogue,
        containerColor = MaterialTheme.colorScheme.onPrimary,
        title = { Text(text = "Check In") },
        text = {
            Column {
                Text("Enter Student Name")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = studentName,
                    onValueChange = { studentName = it },
                    label = { Text("Name") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
                    )

                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("for the class: ")
                Spacer(modifier = Modifier.height(8.dp))
                Text(curr.title + " - " + curr.startTime)
                Text(dateTime)

            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(studentName) },
                colors = ButtonDefaults.textButtonColors(MaterialTheme.colorScheme.primary)) {
                Text("Check In", color = MaterialTheme.colorScheme.onPrimary)
            }
        },
        dismissButton = {
            TextButton(onClick = closeDialogue) {
                Text("Cancel")
            }
        }
    )
}

