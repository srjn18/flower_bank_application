package com.example.surveyapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surveyapplication.ui.theme.*

@Composable
fun SurveyFlowScreen(onClose: () -> Unit) {
    var currentStep by remember { mutableStateOf(1) }
    val totalSteps = 8

    val stepTitles = listOf(
        "PEOPLE",
        "IDENTITY",
        "LOCATION",
        "HOUSEHOLD & PLANTS",
        "CROP & MARKET",
        "SOCIAL & LIVING",
        "PHOTOS & VIDEO",
        "REMARKS & REVIEW"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GreenLight)
    ) {
        // Shared Top App Bar for survey
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onClose) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Close", tint = TextPrimary)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("New Survey", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextPrimary)
        }

        // Shared Progress Bar
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Text("STEP $currentStep OF $totalSteps • ${stepTitles[currentStep - 1]}", color = GreenPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { currentStep.toFloat() / totalSteps },
                modifier = Modifier.fillMaxWidth().height(4.dp),
                color = GreenPrimary,
                trackColor = Color(0xFFE0E0E0)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dynamic Content based on step
        Box(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            when (currentStep) {
                1 -> StepPeople()
                2 -> StepIdentity()
                3 -> StepLocation() // My existing LocationScreen content
                4 -> StepHousehold()
                5 -> StepCrop()
                6 -> StepSocial()
                7 -> StepPhotos()
                8 -> StepReview()
            }
        }

        // Shared Bottom Bar
        Surface(
            color = Color.White,
            shadowElevation = 8.dp
        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    if (currentStep > 1) {
                        Button(
                            onClick = { currentStep-- },
                            colors = ButtonDefaults.buttonColors(containerColor = GreenLight, contentColor = GreenPrimary),
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Back", fontWeight = FontWeight.Bold)
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    
                    Button(
                        onClick = { if (currentStep < totalSteps) currentStep++ else onClose() },
                        colors = ButtonDefaults.buttonColors(containerColor = OrangeAction),
                        modifier = Modifier.weight(2f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if (currentStep < totalSteps) "Next: ${stepTitles[currentStep].split(" ")[0]} (${currentStep+1}/$totalSteps)" else "Submit Survey", fontWeight = FontWeight.Bold)
                        if (currentStep < totalSteps) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next", modifier = Modifier.size(18.dp))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Save, contentDescription = "Save as Draft", tint = TextSecondary, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Save as Draft", color = TextSecondary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun StepPeople() {
    Column {
        Text("Volunteer Details", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = "Ramesh Patil", onValueChange = {}, label = { Text("Volunteer Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "+91 9876543210", onValueChange = {}, label = { Text("Volunteer Contact") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Text("Customer Details", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Customer Full Name") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("House No. & Address") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun StepIdentity() {
    Column {
        Text("Identity Verification", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Aadhaar Number") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Card(modifier = Modifier.weight(1f).aspectRatio(1f), colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Aadhaar Front") }
            }
            Card(modifier = Modifier.weight(1f).aspectRatio(1f), colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Aadhaar Back") }
            }
        }
    }
}

@Composable
fun StepLocation() {
    Column {
        Text("Customer Field Location", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Text("Map coordinates and boundaries", fontSize = 14.sp, color = TextSecondary)
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth().height(200.dp).background(Color.LightGray, RoundedCornerShape(16.dp)), contentAlignment = Alignment.Center) {
            Text("Map View")
        }
    }
}

@Composable
fun StepHousehold() {
    Column {
        Text("Household & Plants", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Number of Family Members") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Number of Plants") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Planting Since (Year)") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun StepCrop() {
    Column {
        Text("Crop & Market", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Crop Production (Season)") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Present Market") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Transport Type") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun StepSocial() {
    Column {
        Text("Social & Living", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Water Facility") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = "", onValueChange = {}, label = { Text("Self Help Group") }, modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun StepPhotos() {
    Column {
        Text("Photos & Video", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Take 6 Photos and 1 short video")
    }
}

@Composable
fun StepReview() {
    Column {
        Text("Review & Submit", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        Spacer(modifier = Modifier.height(16.dp))
        Text("All details are ready to be saved securely.")
    }
}
