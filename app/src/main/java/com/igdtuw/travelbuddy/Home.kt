package com.igdtuw.travelbuddy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController


@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            NavBar(title = "Tripsy", navController = navController, showBackButton = false)
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFFDFBD4)) // light pastel yellow
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tripsy",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF013220),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Add your logo here
            Image(
                painter = painterResource(id = R.drawable.tripsy_logo), // Replace with your logo
                contentDescription = "Tripsy Logo",
                modifier = Modifier
                    .height(120.dp)
                    .padding(bottom = 32.dp)
            )

            MenuOption(text = "Plan a Trip") {
                navController.navigate("DestinationPickerScreen")
            }
            Spacer(modifier = Modifier.height(16.dp))

            MenuOption(text = "Ongoing Trip") { /* Coming Soon */ }
            Spacer(modifier = Modifier.height(16.dp))

            MenuOption(text = "Past Trip Activities") { /* Coming Soon */ }
        }
    }
}

@Composable
fun MenuOption(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF013220)), // dark green
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        )
    }
}
@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    val fakeNavController = rememberNavController()
    HomeScreen(navController = fakeNavController)
}

