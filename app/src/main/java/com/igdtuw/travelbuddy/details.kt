package com.igdtuw.travelbuddy

import android.app.DatePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scratch3(navController: NavController) {
    val context = LocalContext.current

    var finalDestination by remember { mutableStateOf("Paris") }
    var travelDates by remember { mutableStateOf("") }
    var numberOfTravelers by remember { mutableStateOf("2") }

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                travelDates = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            },
            year, month, day
        )
    }

    val ageGroups = listOf("Under 13", "13-18", "19-30", "30-45", "Above 45")
    val ageSelections = remember { mutableStateMapOf<String, Boolean>() }
    ageGroups.forEach { ageSelections.putIfAbsent(it, false) }

    val activityOptions = mapOf(
        "Paris" to listOf("Museum Tours", "Seine Cruise", "Wine Tasting", "Cycling Tours"),
        "Manali" to listOf("Paragliding", "Trekking", "Skiing", "Rafting"),
        "Goa" to listOf("Snorkeling", "Beach Parties", "Jet Skiing", "Scuba Diving")
    )
    val activitiesForDestination = activityOptions[finalDestination] ?: emptyList()
    val selectedActivityIndexes by remember { mutableStateOf(BooleanArray(activitiesForDestination.size)) }
    var showActivityDialog by remember { mutableStateOf(false) }

    val travelModes = listOf("By Road", "By Plane", "By Train")
    var selectedTravelMode by remember { mutableStateOf(travelModes[0]) }
    var expandedMode by remember { mutableStateOf(false) }

    val viewOptions = listOf("Nature", "Forts", "Activities", "Museum", "Other")
    val preferences = remember { mutableStateListOf<String?>(null, null, null) }
    val otherText = remember { mutableStateOf("") }
    val expandedDropdowns = remember { mutableStateListOf(false, false, false) }

    val energyLevels = listOf("Low 💤", "Medium ⚡", "High 🔋")
    val dayEnergy = remember { mutableStateListOf(1f, 1f, 1f) }

    val moodLevels = listOf("Tired 😴", "Chill 😌", "Excited 🤩")
    val dayMoods = remember { mutableStateListOf(1f, 1f, 1f) }

    val selectedActivities = activitiesForDestination
        .filterIndexed { index, _ -> selectedActivityIndexes.getOrNull(index) == true }

    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme.copy(background = Color(0xFFF0F4C3))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Review Your Trip", style = MaterialTheme.typography.headlineMedium, color = Color(0xFF43A047))

            OutlinedTextField(
                value = finalDestination,
                onValueChange = { finalDestination = it },
                label = { Text("Final Destination") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = travelDates,
                onValueChange = { travelDates = it },
                label = { Text("Travel Dates") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { datePickerDialog.show() },
                readOnly = true
            )

            OutlinedTextField(
                value = numberOfTravelers,
                onValueChange = { numberOfTravelers = it },
                label = { Text("Number of Travelers") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text("Age Group(s):", style = MaterialTheme.typography.bodyLarge)
            ageGroups.forEach { group ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = ageSelections[group] == true,
                        onCheckedChange = { ageSelections[group] = it }
                    )
                    Text(group)
                }
            }

            Text("What activities are you interested in?", style = MaterialTheme.typography.bodyLarge, color = Color(0xFF1976D2))
            Button(onClick = { showActivityDialog = true }) {
                Text("Choose Activities")
            }

            if (showActivityDialog) {
                AlertDialog(
                    onDismissRequest = { showActivityDialog = false },
                    confirmButton = {
                        TextButton(onClick = { showActivityDialog = false }) {
                            Text("Done")
                        }
                    },
                    title = { Text("Select Activities") },
                    text = {
                        Column {
                            activitiesForDestination.forEachIndexed { index, activity ->
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = selectedActivityIndexes[index],
                                        onCheckedChange = { isChecked ->
                                            selectedActivityIndexes[index] = isChecked
                                        }
                                    )
                                    Text(text = activity)
                                }
                            }
                        }
                    }
                )
            }

            if (selectedActivities.isNotEmpty()) {
                Text("Selected Activities:", style = MaterialTheme.typography.bodyLarge, color = Color(0xFF1976D2))
                Text(selectedActivities.joinToString(", "))
            }

            Text("Preferred Mode of Travel:", style = MaterialTheme.typography.bodyLarge)
            ExposedDropdownMenuBox(expanded = expandedMode, onExpandedChange = { expandedMode = !expandedMode }) {
                OutlinedTextField(
                    value = selectedTravelMode,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Mode of Travel") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedMode) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(expanded = expandedMode, onDismissRequest = { expandedMode = false }) {
                    travelModes.forEach { mode ->
                        DropdownMenuItem(
                            text = { Text(mode) },
                            onClick = {
                                selectedTravelMode = mode
                                expandedMode = false
                            }
                        )
                    }
                }
            }

            Text("What do you prefer seeing? (Rank Top 3)", style = MaterialTheme.typography.bodyLarge)
            (0..2).forEach { index ->
                val selectedOption = preferences[index]
                ExposedDropdownMenuBox(
                    expanded = expandedDropdowns[index],
                    onExpandedChange = { expandedDropdowns[index] = !expandedDropdowns[index] }
                ) {
                    OutlinedTextField(
                        value = selectedOption ?: "",
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("${index + 1}${if (index == 0) "st" else if (index == 1) "nd" else "rd"} Preference")
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedDropdowns[index])
                        },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedDropdowns[index],
                        onDismissRequest = { expandedDropdowns[index] = false }
                    ) {
                        viewOptions.filterNot { it in preferences || it.isNullOrBlank() }.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    preferences[index] = option
                                    expandedDropdowns[index] = false
                                }
                            )
                        }
                    }
                }

                if (preferences[index] == "Other") {
                    OutlinedTextField(
                        value = otherText.value,
                        onValueChange = { otherText.value = it },
                        label = { Text("Please specify") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Text("Estimated Energy Levels (per day):", style = MaterialTheme.typography.bodyLarge)
            (0..2).forEach { index ->
                Text("Day ${index + 1}: ${energyLevels[dayEnergy[index].toInt()]}")
                Slider(
                    value = dayEnergy[index],
                    onValueChange = { dayEnergy[index] = it },
                    valueRange = 0f..2f,
                    steps = 1,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Text("Expected Mood (per day):", style = MaterialTheme.typography.bodyLarge)
            (0..2).forEach { index ->
                Text("Day ${index + 1}: ${moodLevels[dayMoods[index].toInt()]}")
                Slider(
                    value = dayMoods[index],
                    onValueChange = { dayMoods[index] = it },
                    valueRange = 0f..2f,
                    steps = 1,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Back")
                }
                Button(onClick = {
                    val selectedAges = ageSelections.filter { it.value }.keys
                    val finalPrefs = preferences.mapNotNull {
                        if (it == "Other") otherText.value.takeIf { it.isNotBlank() } else it
                    }
                    val energySummary = dayEnergy.mapIndexed { i, v -> "Day ${i + 1}: ${energyLevels[v.toInt()]}" }
                    val moodSummary = dayMoods.mapIndexed { i, v -> "Day ${i + 1}: ${moodLevels[v.toInt()]}" }

                    Toast.makeText(
                        context,
                        "Trip to $finalDestination\nDates: $travelDates\nTravelers: $numberOfTravelers\nAges: ${selectedAges.joinToString()}\nMode: $selectedTravelMode\nPrefs: ${finalPrefs.joinToString()}\nEnergy: ${energySummary.joinToString()}\nMood: ${moodSummary.joinToString()}",
                        Toast.LENGTH_LONG
                    ).show()
                }) {
                    Text("Plan My Trip!")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Scratch3Preview() {
    val navController = rememberNavController()
    Scratch3(navController = navController)
}

