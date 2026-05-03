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
import com.example.mainproject_cs361.data.repo.features.schedule.ClassRepository
import com.example.mainproject_cs361.utils.MockClassRepository
import com.example.mainproject_cs361.data.model.domain.Class
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailySchedule(
    day: Date?,
    screen: String,
    onCheckInConfirm: (String, Class) -> Unit = { _, _ -> }
) {
    val repository: ClassRepository = MockClassRepository()
    val classes = repository.getClasses(day)
    var showCheckIn by remember { mutableStateOf(false) }
    var showRegistration by remember { mutableStateOf(false) }
    var clickedClass by remember { mutableStateOf(classes[0]) }

    Column (
        modifier = Modifier.padding(2.dp)
    ){
        for (item in classes) {
            ListItem(
                headlineContent = { Text(item.title + ": " + item.startTime) },
                supportingContent = { item.subtitle?.let { Text(it) } },
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

        if(showCheckIn){
            CheckIn(
                clickedClass,
                day,
                closeDialogue = { showCheckIn = false },
                onConfirm = { name ->
                    onCheckInConfirm(name, clickedClass)
                    showCheckIn = false
                }
            )
        }
        if(showRegistration){
            Register(
                closeDialogue = { showRegistration = false }
            )
        }
    }
}

