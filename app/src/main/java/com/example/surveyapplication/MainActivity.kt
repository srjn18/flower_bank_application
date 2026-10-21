package com.example.surveyapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.surveyapplication.ui.screens.HomeScreen
import com.example.surveyapplication.ui.screens.SurveyFlowScreen
import com.example.surveyapplication.ui.theme.SurveyApplicationTheme
import com.example.surveyapplication.ui.theme.GreenPrimary
import com.example.surveyapplication.ui.theme.TextSecondary
import com.example.surveyapplication.ui.theme.TextPrimary
import androidx.compose.ui.graphics.Color
import com.example.surveyapplication.ui.theme.GreenLight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SurveyApplicationTheme {
                SurveyApp()
            }
        }
    }
}

sealed class BottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
    object Surveys : BottomNavItem("surveys", Icons.AutoMirrored.Filled.List, "Surveys")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}

@Composable
fun SurveyApp() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Define main top-level destinations where Bottom Bar should be shown
    val topLevelRoutes = listOf(
        BottomNavItem.Home.route,
        BottomNavItem.Surveys.route,
        BottomNavItem.Profile.route
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute in topLevelRoutes) {
                NavigationBar(
                    containerColor = Color.White,
                    contentColor = TextPrimary
                ) {
                    val items = listOf(
                        BottomNavItem.Home,
                        BottomNavItem.Surveys,
                        BottomNavItem.Profile
                    )
                    items.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentRoute == item.route,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = GreenPrimary,
                                selectedTextColor = GreenPrimary,
                                unselectedIconColor = TextSecondary,
                                unselectedTextColor = TextSecondary,
                                indicatorColor = GreenLight
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    onStartSurvey = {
                        navController.navigate("location_step")
                    }
                )
            }
            composable(BottomNavItem.Surveys.route) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Text("Surveys List Screen")
                }
            }
            composable(BottomNavItem.Profile.route) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Text("Profile Screen")
                }
            }
            composable("location_step") {
                SurveyFlowScreen(
                    onClose = { navController.popBackStack() }
                )
            }

        }
    }
}
