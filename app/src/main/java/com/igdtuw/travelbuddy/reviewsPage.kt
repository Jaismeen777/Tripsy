package com.igdtuw.travelbuddy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun ReviewsPage(
    navController: NavController,
    tripViewModel: TripViewModel = viewModel()
) {
    val destinationName by tripViewModel.destinationName.collectAsState()
    val startDate by tripViewModel.startDate.collectAsState()
    val numberOfTravelers by tripViewModel.numberOfTravelers.collectAsState()
    val numberOfDays by tripViewModel.numberOfDays.collectAsState()
    val ageGroupList by tripViewModel.ageGroupList.collectAsState()
    val category by tripViewModel.category.collectAsState()
    val budget by tripViewModel.budget.collectAsState()
    val energyLevel by tripViewModel.energyLevel.collectAsState()
    val mood by tripViewModel.mood.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFFDFBD4)), // 🌟 Cream background
        horizontalAlignment = Alignment.Start
    ) {
        // Trip Summary Title
        Text(
            text = "Trip Summary",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp),
            color = Color(0xFF013220) // 🌟 Dark green text
        )

        // Trip Details
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF013220)),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                if (destinationName.isNotEmpty()) {
                    Text(
                        text = "Destination: $destinationName",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
                if (startDate.isNotEmpty()) {
                    Text(
                        text = "Dates: $startDate",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
                if (numberOfTravelers.isNotEmpty()) {
                    Text(
                        text = "Number of Travelers: $numberOfTravelers",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
                if (numberOfDays.isNotEmpty()) {
                    Text(
                        text = "Number of Days: $numberOfDays",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
                if (ageGroupList.isNotEmpty()) {
                    Text(
                        text = "Age Group(s): ${ageGroupList.joinToString(", ")}",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
                if (category.isNotEmpty()) {
                    Text(
                        text = "Category: $category",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
                if (budget.isNotEmpty()) {
                    Text(
                        text = "Budget: $budget",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
                if (energyLevel.isNotEmpty()) {
                    Text(
                        text = "Energy Level: $energyLevel",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
                if (mood.isNotEmpty()) {
                    Text(
                        text = "Mood: $mood",
                        fontSize = 18.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Confirm Trip Button
        Button(
            onClick = {
                navController.navigate("home")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220))
        ) {
            Text("Confirm Trip", color = Color(0xFFFDFBD4)) // 🌟 Cream text on button
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Edit Details Button
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220))
        ) {
            Text("Edit Details", color = Color(0xFFFDFBD4)) // 🌟 Cream text on button
        }

        Spacer(modifier = Modifier.height(32.dp))

        // User Reviews Title
        Text(
            text = "User Reviews",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF013220) // 🌟 Dark green text
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Sample Review 1
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF013220)),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("⭐⭐⭐⭐☆", style = MaterialTheme.typography.titleMedium, color = Color.White)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Amazing budget trip! Would never rely on anything other than Tripsy.", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sample Review 2
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF013220)),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("⭐⭐⭐☆", style = MaterialTheme.typography.titleMedium, color = Color.White)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Smooth planning, loved it.", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sample Review 3
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF013220)),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text("⭐⭐⭐⭐⭐", style = MaterialTheme.typography.titleMedium, color = Color.White)
                Spacer(modifier = Modifier.width(12.dp))
                Text("Perfect app for last-minute plans!", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewsPagePreview() {
    val navController = rememberNavController()
    val viewModel: TripViewModel = viewModel() // Provide a ViewModel for the preview
    ReviewsPage(navController = navController, tripViewModel = viewModel)
}