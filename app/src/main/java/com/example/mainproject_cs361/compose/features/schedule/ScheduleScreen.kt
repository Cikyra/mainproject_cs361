package com.example.mainproject_cs361.compose.features.schedule

import android.icu.util.Calendar
import com.example.mainproject_cs361.compose.composables.DailySchedule
import java.util.Date
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.mainproject_cs361.data.model.domain.Class
import com.example.mainproject_cs361.utils.MockClassRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.plus
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toLocalDateTime
import java.time.ZoneId

@Composable
fun ScheduleScreen(){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        ScheduleScreenContent()
    }
}

@Composable
fun ScheduleScreenContent(){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp),) {
        HorizontalDivider(
            Modifier,
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )

        Text(
            text = "Schedule",
            style = TextStyle(
                fontSize = 25.sp,
            )
        )

        HorizontalDivider(
            Modifier,
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )

        val currentTime = Clock.System.now()
        val today = currentTime.toLocalDateTime(TimeZone.currentSystemDefault()).date
        var selectedDate by remember { mutableStateOf(today) }

        val endDate = today.plus(6, DateTimeUnit.DAY)


        val dates = remember {
            generateSequence(today) { date ->
                val next = date.plus(1, DateTimeUnit.DAY)
                if (next <= endDate) next else null
            }.toList()
        }

        DateScroller(
            dates = dates,
            selectedDate = selectedDate,
            onSelectedDate = { selectedDate = it },
        )

        Text(   text = "Select a class to register",
            style = TextStyle(
                fontSize = 15.sp,
            )
        )

        var studentName by remember { mutableStateOf("") }
        var currClass by remember { mutableStateOf<Class?>(null) }
        val repository = remember { MockClassRepository() }
        var showDialogue by remember { mutableStateOf(false) }
        var showCancel by remember { mutableStateOf(false) }

        DailySchedule(
            day = selectedDate.toDate(),
            screen = "Schedule",
            onCheckInConfirm = { name, clickedClass ->
                studentName = name
                currClass = clickedClass
                showDialogue = true
            }
        )

        if(studentName != ""){
            if(repository.register(studentName, currClass?.id ?: "")){
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
                        Text(text = "Registration Successful", style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(15.dp,10.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "$studentName is now registered for ",
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
                        title = { Text(text = "Register") },
                        text = {
                            Column {
                                Text("$studentName is already registered for: ")
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(currClass?.startTime + " " + currClass?.title)
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Would you like to cancel your registration?")
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
                                Text("Yes, cancel")
                            }
                        }
                    )
                }

                if(showCancel){
                    repository.classRegistry.getValue(studentName).remove(currClass?.id)
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
                            Text(text = "Registration Canceled", style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(15.dp,10.dp))
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Successfully canceled $studentName's registration",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentSize(Alignment.Center)
                                    .padding(10.dp),
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }

    }
}

fun LocalDate.toDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, monthNumber - 1, dayOfMonth, 0, 0, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    return Date(calendar.timeInMillis)
}

@Composable
fun DateScroller(
    dates: List<LocalDate>,
    selectedDate: LocalDate,
    onSelectedDate: (LocalDate) -> Unit
){
    val listState = rememberLazyListState()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top,
        contentPadding = PaddingValues(horizontal = 10.dp)
    ) {
        items(dates.size) { index ->
            val date = dates[index]

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .border(width = 3.dp, color = MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(12.dp))
                    .background(
                        if (date == selectedDate) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary
                    )
                    .clickable { onSelectedDate(date) }
                    .width(80.dp)
                    .height(100.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = date.month.name.take(3),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (date == selectedDate) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onTertiary
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = date.dayOfMonth.toString(),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (date == selectedDate) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary
                        )
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = date.dayOfWeek.name.take(3),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = if (date == selectedDate) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onTertiary
                        )
                    )
                }
            }
        }
    }
}
