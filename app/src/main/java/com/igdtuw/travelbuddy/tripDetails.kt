package com.igdtuw.travelbuddy

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TripDetailsScreen(
    placeName: String,
    navController: NavController,
    tripViewModel: TripViewModel
) {
    val context = LocalContext.current
    val creamColor = Color(0xFFFDFBD4)
    val darkGreen = Color(0xFF013220)

    var numberOfTravelers by remember { mutableIntStateOf(1) }
    var fromDate by remember { mutableStateOf("") }
    var tillDate by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val energyOptions = listOf("Low 💤", "Medium ⚡", "High 🔋")
    var selectedEnergy by remember { mutableStateOf(energyOptions[1]) }
    var energyExpanded by remember { mutableStateOf(false) }

    val moodOptions = listOf("Tired 😴", "Chill 😌", "Excited 🤩")
    var selectedMood by remember { mutableStateOf(moodOptions[1]) }
    var moodExpanded by remember { mutableStateOf(false) }

    val ageGroups = listOf("Under 13", "13-18", "19-30", "30-45", "Above 45")
    val ageSelections = remember { mutableStateMapOf<String, Boolean>().apply {
        ageGroups.forEach { this[it] = false }
    } }

    val categoryOptions = listOf("Family 👨‍👩‍👧‍👦", "Friends 👯‍♂️", "Couple 💑", "Business 💼", "Professional 👔")
    var selectedCategory by remember { mutableStateOf(categoryOptions[0]) }
    var categoryExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(creamColor)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            text = "Trip Details for $placeName",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = darkGreen
        )

        // --- Number of Travelers ---
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Number of Travelers:", fontSize = 16.sp, color = darkGreen)
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = { if (numberOfTravelers > 1) numberOfTravelers-- },
                enabled = numberOfTravelers > 1,
                colors = ButtonDefaults.buttonColors(containerColor = darkGreen)
            ) { Text("-", color = Color.White) }
            Text(
                text = "$numberOfTravelers",
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 12.dp),
                color = darkGreen
            )
            Button(
                onClick = { numberOfTravelers++ },
                colors = ButtonDefaults.buttonColors(containerColor = darkGreen)
            ) { Text("+", color = Color.White) }
        }

        // --- Date Selection (Using Button to Open DatePickerDialog) ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("From Date:", fontWeight = FontWeight.SemiBold, color = darkGreen)
                Button(
                    onClick = {
                        DatePickerDialog(
                            context,
                            { _, selectedYear, selectedMonth, selectedDay ->
                                fromDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = darkGreen),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (fromDate.isEmpty()) "From Date" else fromDate, color = Color.White)
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text("Till Date:", fontWeight = FontWeight.SemiBold, color = darkGreen)
                Button(
                    onClick = {
                        DatePickerDialog(
                            context,
                            { _, selectedYear, selectedMonth, selectedDay ->
                                tillDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                        ).show()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = darkGreen),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (tillDate.isEmpty()) "Till Date" else tillDate, color = Color.White)
                }
            }
        }

        // --- Energy Level Selection (Dropdown) ---
        ExposedDropdownMenuBox(
            expanded = energyExpanded,
            onExpandedChange = { energyExpanded = !energyExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedEnergy,
                onValueChange = {},
                label = { Text("Energy Level", color = darkGreen) },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = energyExpanded) },
                modifier = Modifier.menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = darkGreen,
                    unfocusedBorderColor = darkGreen,
                    focusedLabelColor = darkGreen,
                    unfocusedLabelColor = darkGreen,
                    focusedTextColor = darkGreen,
                    unfocusedTextColor = darkGreen
                )
            )
            ExposedDropdownMenu(
                expanded = energyExpanded,
                onDismissRequest = { energyExpanded = false }
            ) {
                energyOptions.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            selectedEnergy = it
                            energyExpanded = false
                        }
                    )
                }
            }
        }

        // --- Mood Selection (Dropdown) ---
        ExposedDropdownMenuBox(
            expanded = moodExpanded,
            onExpandedChange = { moodExpanded = !moodExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedMood,
                onValueChange = {},
                label = { Text("Mood", color = darkGreen) },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = moodExpanded) },
                modifier = Modifier.menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = darkGreen,
                    unfocusedBorderColor = darkGreen,
                    focusedLabelColor = darkGreen,
                    unfocusedLabelColor = darkGreen,
                    focusedTextColor = darkGreen,
                    unfocusedTextColor = darkGreen
                )
            )
            ExposedDropdownMenu(
                expanded = moodExpanded,
                onDismissRequest = { moodExpanded = false }
            ) {
                moodOptions.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            selectedMood = it
                            moodExpanded = false
                        }
                    )
                }
            }
        }

        // --- Category of People Selection (Dropdown for Family, Friends, etc.) ---
        ExposedDropdownMenuBox(
            expanded = categoryExpanded,
            onExpandedChange = { categoryExpanded = !categoryExpanded },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = selectedCategory,
                onValueChange = {},
                label = { Text("Category of People", color = darkGreen) },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                modifier = Modifier.menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = darkGreen,
                    unfocusedBorderColor = darkGreen,
                    focusedLabelColor = darkGreen,
                    unfocusedLabelColor = darkGreen,
                    focusedTextColor = darkGreen,
                    unfocusedTextColor = darkGreen
                )
            )
            ExposedDropdownMenu(
                expanded = categoryExpanded,
                onDismissRequest = { categoryExpanded = false }
            ) {
                categoryOptions.forEach {
                    DropdownMenuItem(
                        text = { Text(it) },
                        onClick = {
                            selectedCategory = it
                            categoryExpanded = false
                        }
                    )
                }
            }
        }

        // --- Age Groups ---
        Text("Age Groups:", fontWeight = FontWeight.SemiBold, color = darkGreen)
        Column(horizontalAlignment = Alignment.Start) {
            ageGroups.forEach { group ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = ageSelections[group] == true,
                        onCheckedChange = { ageSelections[group] = it },
                        colors = CheckboxDefaults.colors(checkedColor = darkGreen)
                    )
                    Text(group, color = darkGreen)
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                // Update the TripViewModel with the collected data
                tripViewModel.setDestinationName(placeName)
                tripViewModel.setStartDate(fromDate) // You might want to combine fromDate and tillDate
                tripViewModel.setNumberOfTravelers(numberOfTravelers.toString())
                // You'll need to add StateFlows and setters for energy, mood, and age groups in your ViewModel
                 tripViewModel.setEnergyLevel(selectedEnergy)
                 tripViewModel.setMood(selectedMood)
                 tripViewModel.setAgeGroups(ageSelections.filterValues { it }.keys.toList())
                tripViewModel.setCategory(selectedCategory)

                navController.navigate("TravelModeScreen")
            },
            colors = ButtonDefaults.buttonColors(containerColor = darkGreen),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Continue", color = Color.White)
        }
    }
}