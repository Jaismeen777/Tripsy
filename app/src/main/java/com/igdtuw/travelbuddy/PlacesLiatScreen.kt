package com.igdtuw.travelbuddy

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.igdtuw.travelbuddy.selectedPlaceName
import androidx.navigation.NavController
import com.igdtuw.travelbuddy.utils.fetchPlacesByType

@Composable
fun PlacesListScreen(category: String, navController: NavController) {
    val context = LocalContext.current
    var placeNames by remember { mutableStateOf<List<String>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Fetch data when screen appears or category changes
    LaunchedEffect(category) {
        fetchPlacesByType(
            type = category,
            onSuccess = { fetchedNames ->
                placeNames = fetchedNames
                errorMessage = null
            },
            onFailure = { exception ->
                errorMessage = "Error fetching places: ${exception.localizedMessage}"
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "$category Places",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        LazyColumn {
            items(placeNames) { placeName ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable {
                            selectedPlaceName = placeName  // ✅ Save the place
                            navController.navigate("tripDetails/$placeName") // ✅ Navigate to itinerary screen
                        },
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Text(
                        text = placeName,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
