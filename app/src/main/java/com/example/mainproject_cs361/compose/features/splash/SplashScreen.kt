package com.example.mainproject_cs361.compose.features.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.size
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.Alignment
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import com.example.mainproject_cs361.R
import androidx.compose.foundation.layout.padding

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                modifier = Modifier.size(250.dp)
                    .padding(top = 100.dp),
                painter = painterResource(id = R.drawable.team_stacked_redblack),
                contentDescription = "TMA Logo"
            )
        }
        Text("We Believe\n\n\nYou Belong",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                .padding(top = 100.dp)
        )

        Button(onClick = {
            onTimeout()
        },
            modifier = Modifier.padding(top = 100.dp)
                .align(alignment = Alignment.CenterHorizontally)
                .size(width = 330.dp, height = 60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )) {
            Text("Let's Go",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold)
        }
    }
}
