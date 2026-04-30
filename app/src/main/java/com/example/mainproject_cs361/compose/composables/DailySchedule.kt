package com.example.mainproject_cs361.compose.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mainproject_cs361.R
import com.example.mainproject_cs361.data.repo.features.schedule.ClassRepository
import com.example.mainproject_cs361.utils.MockClassRepository
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailySchedule(day: Date?) {
    val repository: ClassRepository = MockClassRepository()
    val classes = repository.getClasses(day)

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
                        //TODO: click on class
                    }
                    .padding(horizontal = 10.dp,
                                vertical = 3.dp)
                    .requiredHeight(90.dp)
            )
        }
    }
}

