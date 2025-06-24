package com.igdtuw.travelbuddy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

val Cream = Color(0xFFFDFBD4)
val DarkGreen = Color(0xFF013220)

@Composable
fun BudgetInputScreen(navController: NavHostController? = null) {
    val budgetItems = listOf("Hotel", "Travel", "Intrasite Travel", "Food", "Shopping", "Miscellaneous")
    val budgets = remember { mutableStateMapOf<String, Float>() }

    budgetItems.forEach { item ->
        if (budgets[item] == null) {
            budgets[item] = 5000f
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cream)
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🎯 Let's Plan Your Budget!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGreen,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        budgetItems.forEachIndexed { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF40826D)),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "💰 ${index + 1}. $item Budget",
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            fontSize = 18.sp,
                        )
                        Text(
                            text = "₹${budgets[item]?.toInt() ?: 0}",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Slider(
                        value = budgets[item] ?: 0f,
                        onValueChange = { newValue -> budgets[item] = newValue },
                        valueRange = 0f..100000f,
                        modifier = Modifier.fillMaxWidth(),
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Cyan,
                            activeTrackColor = DarkGreen,
                            inactiveTrackColor = Cream
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                navController?.navigate("itineraray")
            },
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "🚀 Generate Itinerary",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BudgetInputScreenPreview() {
    BudgetInputScreen()
}