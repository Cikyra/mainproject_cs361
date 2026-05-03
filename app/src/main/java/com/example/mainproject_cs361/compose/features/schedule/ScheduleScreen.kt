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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mainproject_cs361.data.model.domain.Class
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

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
        DailySchedule(
            day = Calendar.getInstance().time,
            screen = "Schedule",
            onCheckInConfirm = { name, clickedClass ->
                studentName = name
                currClass = clickedClass
            }
        )

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
