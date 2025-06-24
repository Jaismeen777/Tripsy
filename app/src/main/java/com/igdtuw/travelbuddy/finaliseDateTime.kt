package com.igdtuw.travelbuddy

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinaliseDateTimeScreen(navController: NavHostController) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            NavBar(
                title = "Finalize Date & Time",
                navController = navController
            )
        },
        containerColor = Color(0xFFFDFBD4) // 🌟 Cream background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
                .background(Color(0xFFFDFBD4)), // 🌟 Cream background
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pick Date
            Button(
                onClick = {
                    DatePickerDialog(
                        context,
                        { _, year, month, day ->
                            selectedDate = "$day/${month + 1}/$year"
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF013220),
                    contentColor = Color(0xFFFDFBD4)
                )
            ) {
                Text("Pick a Date", color = Color(0xFFFDFBD4))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Pick Time
            Button(
                onClick = {
                    TimePickerDialog(
                        context,
                        { _, hour, minute ->
                            selectedTime = String.format("%02d:%02d", hour, minute)
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF013220),
                    contentColor = Color(0xFFFDFBD4)
                )
            ) {
                Text("Pick a Time", color = Color(0xFFFDFBD4))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Selected Date: $selectedDate")
            Text("Selected Time: $selectedTime")

            Spacer(modifier = Modifier.height(24.dp))

            // Confirm Button
            Button(
                onClick = {
                    if (selectedDate.isNotBlank() && selectedTime.isNotBlank()) {
                        Toast.makeText(context, "Trip planned on $selectedDate at $selectedTime", Toast.LENGTH_SHORT).show()
                        navController.navigate("reviewsPage")
                    } else {
                        Toast.makeText(context, "Please select both date and time", Toast.LENGTH_SHORT).show()
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF013220),
                    contentColor = Color(0xFFFDFBD4)
                )
            ) {
                Text("Confirm", color = Color(0xFFFDFBD4))
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Can't plan the trip? Let us know why:", fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            val reasons = listOf("Bad Weather", "High Traffic", "Closed Today")
            reasons.forEach { reason ->
                Button(
                    onClick = {
                        Toast.makeText(context, "Marked as unsuitable due to: $reason", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(vertical = 4.dp),
                    shape = MaterialTheme.shapes.medium,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF013220),
                        contentColor = Color(0xFFFDFBD4)
                    )
                ) {
                    Text(
                        text = reason,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = Color(0xFFFDFBD4)
                    )
                }
            }
        }
    }
}
