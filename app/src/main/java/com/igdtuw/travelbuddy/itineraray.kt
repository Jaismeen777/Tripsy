package com.igdtuw.travelbuddy // <-- Make sure this is your correct package name

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager // Use Foundation Pager
import androidx.compose.foundation.pager.rememberPagerState // Use Foundation Pager State
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape // Import RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.compose.ui.draw.clip
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.igdtuw.travelbuddy.selectedPlaceName
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Remove the Accompanist import: import com.google.accompanist.pager.*
import com.igdtuw.travelbuddy.R // <-- Import your project's R class (verify package name)

// Sample data for itineraries and images
// 1) YOUR ITINERARY DATA
 private val itineraries = mapOf(
    "Manali"      to listOf(
        "Day 1: Arrival in Manali → Hadimba Devi Temple → Mall Road",
        "Day 2: Solang Valley → Nehru Kund",
        "Day 3: Jogini Falls → Lama Dugh (Trekking)"
    ),
    "Shimla"      to listOf(
        "Day 1: Arrival → Jakhu Temple → The Ridge → Mall Road",
        "Day 2: Kufri → Himalayan Nature Park → Christ Church",
        "Day 3: Chadwick Falls → State Museum → Departure"
    ),
    "Coorg"       to listOf(
        "Day 1: Abbey Falls → Raja's Seat → Madikeri Fort",
        "Day 2: Dubare Elephant Camp → Golden Temple → Coffee Plantation Tour",
        "Day 3: Talacauvery → Bhagamandala → Departure"
    ),
    "Cherrapunji"  to listOf(
        "Day 1: Umiam Lake → Hotel check‑in",
        "Day 2: Nohkalikai Falls → Mawsmai Cave → Seven Sister Falls",
        "Day 3: Return to Guwahati → Departure"
    ),
    "Jaisalmer"   to listOf(
        "Day 1: Jaisalmer Fort visit",
        "Day 2: Desert Safari + Camel Ride + Cultural Dinner",
        "Day 3: Havelis & Gadisar Lake → Departure"
    ),
    "North Goa"   to listOf(
        "Day 1: Calangute Beach → Hotel check‑in",
        "Day 2: Fort Aguada → Baga → Anjuna",
        "Day 3: Panjim → Dudhsagar Falls → Departure"
    ),
    "Pondicherry" to listOf(
        "Day 1: White Town → Promenade Beach",
        "Day 2: Auroville → Matrimandir → Serenity Beach",
        "Day 3: Paradise Beach → Boathouse → Arikamedu"
    ),
    "Varkala"     to listOf(
        "Day 1: Varkala Beach → Cliff → Sunset",
        "Day 2: Janardanaswamy Temple → Anjengo Fort",
        "Day 3: Kappil Beach → Backwaters boat ride → Market"
    ),
    "Andaman"     to listOf(
        "Day 1: Cellular Jail → Corbyn's Cove",
        "Day 2: Havelock Island → Radhanagar + Elephant Beach",
        "Day 3: Neil Island → Bharatpur + Laxmanpur Beach"
    ),
    "Maldives"    to listOf(
        "Day 1: Resort check‑in → Relax",
        "Day 2: Snorkeling/Diving → Sunset cruise",
        "Day 3: Island‑hop/Spa → Departure"
    ),
    "Lakshadweep" to listOf(
        "Day 1: Agatti → Resort relax",
        "Day 2: Snorkeling/Scuba → Beach sunset",
        "Day 3: Island‑hop → Market → Departure"
    ),
    "Delhi"       to listOf(
        "Day 1: Old Delhi exploration",
        "Day 2: India Gate → Humayun's Tomb → Markets",
        "Day 3: Qutub Minar → Lotus Temple → Departure"
    ),
    "Jaipur"      to listOf(
        "Day 1: Amer Fort → Hotel",
        "Day 2: City Palace → Jantar Mantar → Bazaars",
        "Day 3: Hawa Mahal → Jal Mahal → Departure"
    ),
    "Amritsar"    to listOf(
        "Day 1: Golden Temple → Hotel",
        "Day 2: Jallianwala Bagh → Wagah Border → Markets",
        "Day 3: Durgiana Temple → Departure"
    ),
    "Rameshwaram" to listOf(
        "Day 1: Ramanathaswamy Temple → Hotel",
        "Day 2: Dhanushkodi → Local Temples",
        "Day 3: Jatayu Tirtham → Departure"
    ),
    "Bangalore"   to listOf(
        "Day 1: Bangalore Palace → Hotel",
        "Day 2: Lalbagh → MG Road",
        "Day 3: Tipu Sultan's Fort → Departure"
    ),
    "New York"    to listOf(
        "Day 1: Statue of Liberty → Hotel",
        "Day 2: Times Square → Central Park → Fifth Avenue",
        "Day 3: Empire State Building → Departure"
    ),
    "London"      to listOf(
        "Day 1: Tower of London → Hotel",
        "Day 2: Buckingham Palace → Westminster → Oxford Street",
        "Day 3: British Museum → Departure"
    ),
    "Gulmarg"     to listOf(
        "Day 1: Hotel → Relax",
        "Day 2: Gondola → Skiing/Snowboarding",
        "Day 3: Apharwat Peak → Departure"
    ),
    "Zurich"      to listOf(
        "Day 1: Old Town → Hotel",
        "Day 2: Lake Zurich → Uetliberg",
        "Day 3: Museums → Departure"
    ),
    "Gstaad"      to listOf(
        "Day 1: Town Exploration → Hotel",
        "Day 2: Skiing/Hiking",
        "Day 3: Scenic Villages → Departure"
    ),
    "Rishikesh"   to listOf(
        "Day 1: Town → Hotel",
        "Day 2: White‑water Rafting",
        "Day 3: Bungee/Giant Swing → Departure"
    )
)

// 2) YOUR PLACE → DRAWABLE MAP
// Ensure all these drawables exist in res/drawable and R is imported correctly
private val placeImages: Map<String, Int> = mapOf(
    "Manali"      to R.drawable.manali,
    "Shimla"      to R.drawable.shimla,
    "Coorg"       to R.drawable.coorg,
    "Cherrapunji"  to R.drawable.cherrapunji,
    "Jaisalmer"   to R.drawable.jaisalmer,
    "North Goa"   to R.drawable.north_goa,
    "Pondicherry" to R.drawable.pondicherry,
    "Varkala"     to R.drawable.varkala,
    "Andaman"     to R.drawable.andaman,
    "Maldives"    to R.drawable.maldives,
    "Lakshadweep" to R.drawable.lakshadweep,
    "Delhi"       to R.drawable.delhi,
    "Jaipur"      to R.drawable.jaipur,
    "Amritsar"    to R.drawable.amritsar,
    "Rameshwaram" to R.drawable.rameshwaram,
    "Bangalore"   to R.drawable.bangalore,
    "New York"    to R.drawable.new_york,
    "London"      to R.drawable.london,
    "Gulmarg"     to R.drawable.gulmarg,
    "Zurich"      to R.drawable.zurich,
    "Gstaad"      to R.drawable.gstaad,
    "Rishikesh"   to R.drawable.rishikesh
)
@OptIn(ExperimentalFoundationApi::class) // Use Foundation API annotation
@Composable
fun ItineraryScreen(placeName: String, onProceed: () -> Unit) {
    val place = selectedPlaceName ?: "Unknown"
    val itinerary = itineraries[place] ?: listOf("No itinerary found for $placeName")
    val imageResId = placeImages[place] ?: 0 // Default to 0 if not found

    // Theme colors
    val pageBackground = Color(0xFFFDFBD4)
    val cardColor = Color(0xFF013220)
    val cardTextColor = Color(0xFFFDFBD4)

    // Pager state for swipeable cards - using Foundation Pager's rememberPagerState
    val pagerState = rememberPagerState(pageCount = { itinerary.size })

    Scaffold(
        containerColor = pageBackground,
        bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .offset(y = (-70).dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick =onProceed,
                    shape = RoundedCornerShape(12.dp), // Use imported RoundedCornerShape
                    modifier = Modifier.fillMaxWidth(0.6f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF013220))
                ) {
                    Text("Proceed", fontSize = 18.sp,color = Color(0xFFfdfbd4))
                }
            }
        }
    ) { innerPadding ->
        // Main layout for the screen
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Place Title
          
            // Image Section (optional, if image exists)
            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId), // Use imported R class
                    contentDescription = "$placeName image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(RoundedCornerShape(16.dp)) // Use imported RoundedCornerShape
                        .background(Color.LightGray) // Added for placeholder visibility
                )
            } else {
                // Optional: Show a placeholder Box if imageResId is 0 (not found)
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)
                ) {
                    Text("Image not available", Modifier.align(Alignment.Center), color = Color.DarkGray)
                }
            }

            // Pager for Swipeable Itineraries - using Foundation HorizontalPager
            HorizontalPager(
                state = pagerState
                // Removed 'count' parameter - inferred from pagerState
            ) { page -> // 'page' is the index provided by the Pager
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp), // Added horizontal padding for better spacing between cards
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    shape = RoundedCornerShape(16.dp), // Use imported RoundedCornerShape
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Adjusted elevation
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp), // Padding inside the card
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = itinerary[page], // Access itinerary item using page index
                            color = cardTextColor,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Pager Indicator - REMOVED
            // HorizontalPagerIndicator was part of Accompanist Pager.
            // You need to build a custom one or find a library for Foundation Pager.
            // Example: Add a simple Row of dots below the pager if needed.
            Spacer(modifier = Modifier.height(16.dp)) // Add some space if needed

        }
    }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun PreviewItineraryScreen() {
    // You might want to wrap your preview in a theme if you have one defined
    // YourAppTheme {
    ItineraryScreen(placeName = "MANALI") {
        // Example action for the preview button
        println("Proceed clicked in Preview")
    }
    // }
}