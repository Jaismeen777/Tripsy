package com.igdtuw.travelbuddy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
// Data class for destination item
data class Destination(
    val type: String,
    val iconRes: Int,
    val title: String // Added the title property
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val tripViewModel: TripViewModel = viewModel()
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TravelBuddyApp(tripViewModel)
                }
            }
        }
    }
}
fun fetchPlacesByType(
    type: String,
    onSuccess: (List<String>) -> Unit,
    onFailure: (Exception) -> Unit
) {
    val db = FirebaseFirestore.getInstance()
    db.collection("places")
        .whereEqualTo("type", type)
        .get()
        .addOnSuccessListener { result ->
            val placeNames = result.documents.mapNotNull { doc ->
                doc.getString("name")
            }
            onSuccess(placeNames)
        }
        .addOnFailureListener { exception ->
            onFailure(exception)
        }
}
@Composable
fun TravelBuddyApp(tripViewModel: TripViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "signUp") {

        composable("signUp") {
            SignInScreen(navController)
        }
        composable("loginScreen") {
            LoginScreen(navController)
        }
        composable("Home"){
            HomeScreen(navController)
        }
        composable("DestinationPickerScreen") {
            DestinationPickerScreen(navController)
        }

        composable("PlacesLiatScreen/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            PlacesListScreen(category = category, navController = navController)
        }
        composable("tripDetails/{placeName}") { backStackEntry ->
            val placeName = backStackEntry.arguments?.getString("placeName") ?: ""
            TripDetailsScreen(placeName = placeName,navController = navController,tripViewModel = tripViewModel)
        }
        composable("TravelModeScreen") {
            TravelModeScreen(navController)
        }
        composable("BudgetInputScreen") {
            BudgetInputScreen(navController)
        }
        composable("itineraray") { backStackEntry ->
            val placeName = backStackEntry.arguments?.getString("placeName") ?: ""
            ItineraryScreen(placeName = placeName,// Passing "Paris" as a placeholder for place name
            onProceed = {
                navController.navigate("finaliseDateTime") // Navigate to another screen
            })
        }
        composable("finaliseDateTime") {
            FinaliseDateTimeScreen(navController) // Replace with your actual Composable function
        }
        composable("reviewsPage") {
            ReviewsPage(navController,tripViewModel = tripViewModel)
        }
        composable("tripSummary") {
            TripSummaryScreen()
        }
    }
}
@Composable
fun DestinationPickerScreen(navController: NavController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var selectedType by remember { mutableStateOf<String?>(null) }
    var placeNames by remember { mutableStateOf<List<String>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val destinationOptions = listOf(
        Destination("Mountains", R.drawable.img_10, "mountains"),
        Destination("Beaches", R.drawable.img_9, "beaches"),
        Destination("Forests", R.drawable.img_8, "forests"),
        Destination("Deserts", R.drawable.img_7, "deserts"),
        Destination("Historical Places", R.drawable.img_6, "historical"),
        Destination("Religious Places", R.drawable.img_5, "religious"),
        Destination("Islands", R.drawable.img_4, "islands"),
        Destination("Snowy Places", R.drawable.img_3, "snowy"),
        Destination("Adventurous Places", R.drawable.img_2, "adventurous"),
        Destination("Culture", R.drawable.img_1, "culture"),
        Destination("Wellness Retreat", R.drawable.img, "wellness"),
        )
    Column(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFFDFBD4))
        .padding(16.dp)) {
        Text(
            text = "Choose Destination Type",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF013220),
            modifier = Modifier.padding(bottom = 12.dp)
        )
// Display the destination cards
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(destinationOptions) { destination ->
                DestinationCard(destination = destination) {
// On card click, navigate to the places list screen
                    navController.navigate("PlacesLiatScreen/${destination.type}")
                }
            }
        }
        if (errorMessage != null) {
            Text(
                text = errorMessage ?: "",
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}
@Composable
fun DestinationCard(destination: Destination, onClick: () -> Unit = {}) {
    var clicked by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (clicked) 0.95f else 1f,
        animationSpec = tween(durationMillis = 200),
        label = "CardScale"
    )
    LaunchedEffect(clicked) {
        if (clicked) {
            delay(200)
            clicked = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .graphicsLayer(scaleX = scale, scaleY = scale)
            .clip(RoundedCornerShape(20.dp))
            .clickable {
                clicked = true
                onClick()
            }
    ) {
        Image(
            painter = painterResource(id = destination.iconRes),
            contentDescription = destination.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize()
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Black.copy(alpha = 0.3f))
        )
        Text(
            text = destination.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(12.dp)
        )
    }
}
@Composable
fun TravelModeScreen(navController: NavHostController) {
    val travelModes = listOf("Car", "Train", "Plane", "Bus")
    var selectedMode by remember { mutableStateOf<String?>(null) }
    val travelTips = listOf(
        "✨ Tip: Pack light and carry a reusable water bottle!",
        "💡 Tip: Use public transport to reduce your carbon footprint.",
        "🧳 Tip: Roll your clothes to save luggage space.",
        "📱 Tip: Download offline maps before you travel.",
        "🕐 Tip: Always reach the station/airport early."
    )
    val randomTip = remember { travelTips.random() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFBD4)) // Cream background
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Choose your travel mode",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF013220), // Dark green
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            items(travelModes) { mode ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable {
                            selectedMode = mode
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = if (selectedMode == mode) Color(0xFF015e2c) else Color(0xFF013220)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = mode,
                            color = Color.White,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF013220)),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Text(
                text = randomTip,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(12.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate("BudgetInputScreen")
            },
            enabled = selectedMode != null,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220)),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Continue", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun TripSummaryScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFDFBD4)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Trip Summary Coming Soon!",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF013220)
        )
    }
}
@Preview(showBackground = true)
@Composable
fun DestinationPickerPreview() {
    val navController = rememberNavController()
    DestinationPickerScreen(navController)
}

@Preview(showBackground = true)
@Composable
fun TravelModeScreenPreview() {
    val navController = rememberNavController()
    TravelModeScreen(navController)
}
@Preview(showBackground = true)
@Composable
fun TripSummaryScreenPreview() {
    TripSummaryScreen()
}
