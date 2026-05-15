package com.gramakhata

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gramakhata.ui.screens.*
import com.gramakhata.ui.theme.Forest
import com.gramakhata.ui.theme.GramaKhataTheme
import com.gramakhata.ui.theme.Saffron
import com.gramakhata.viewmodel.GramaViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GramaKhataTheme {
                val navController = rememberNavController()
                val viewModel: GramaViewModel = viewModel()
                
                var showSplash by remember { mutableStateOf(true) }

                if (showSplash) {
                    SplashScreen(onFinish = { showSplash = false })
                } else {
                    MainContent(navController, viewModel)
                }
            }
        }
    }
}

@Composable
fun MainContent(navController: NavHostController, viewModel: GramaViewModel) {
    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            
            // Only show bottom bar on main screens
            if (currentRoute in listOf("dashboard", "reports", "settings")) {
                NavigationBar(containerColor = Color.White, contentColor = Forest) {
                    NavItem(
                        label = "Home",
                        icon = Icons.AutoMirrored.Filled.List,
                        selected = currentRoute == "dashboard",
                        onClick = { navController.navigate("dashboard") }
                    )
                    NavItem(
                        label = "Reports",
                        icon = Icons.Default.Info,
                        selected = currentRoute == "reports",
                        onClick = { navController.navigate("reports") }
                    )
                    NavItem(
                        label = "Settings",
                        icon = Icons.Default.Settings,
                        selected = currentRoute == "settings",
                        onClick = { navController.navigate("settings") }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(padding)
        ) {
            composable("dashboard") {
                DashboardScreen(
                    viewModel = viewModel,
                    onSelectCustomer = { customer -> 
                        // Simplified: pass customer ID as arg if needed, for now just logic
                        navController.navigate("profile/${customer.id}")
                    },
                    onAddTransaction = { navController.navigate("add") }
                )
            }
            composable("profile/{customerId}") { backStackEntry ->
                val customerId = backStackEntry.arguments?.getString("customerId")?.toIntOrNull()
                // In a real app, you'd fetch the customer from VM using ID
                // For this demo, let's assume we can get it.
                // We'll need a way to get a single customer by ID in VM
                val customers by viewModel.customers.collectAsState(initial = emptyList())
                val customer = customers.find { it.id == customerId }
                customer?.let {
                    CustomerProfileScreen(
                        customer = it,
                        viewModel = viewModel,
                        onBack = { navController.popBackStack() },
                        onAddTx = { type ->
                            navController.navigate("add?name=${it.name}&phone=${it.phone}&type=$type")
                        }
                    )
                }
            }
            composable(
                route = "add?name={name}&phone={phone}&type={type}",
                arguments = listOf(
                    navArgument("name") { nullable = true },
                    navArgument("phone") { nullable = true },
                    navArgument("type") { defaultValue = "give" }
                )
            ) { backStackEntry ->
                val name = backStackEntry.arguments?.getString("name")
                val phone = backStackEntry.arguments?.getString("phone")
                val type = backStackEntry.arguments?.getString("type") ?: "give"
                AddTransactionScreen(
                    viewModel = viewModel,
                    initialName = name,
                    initialPhone = phone,
                    initialType = type,
                    onFinish = { navController.popBackStack() }
                )
            }
            composable("reports") {
                ReportsScreen(viewModel = viewModel)
            }
            composable("settings") {
                SettingsScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun RowScope.NavItem(label: String, icon: ImageVector, selected: Boolean, onClick: () -> Unit) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(icon, contentDescription = label) },
        label = { Text(label) },
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = Saffron,
            selectedTextColor = Saffron,
            indicatorColor = Saffron.copy(alpha = 0.1f),
            unselectedIconColor = Forest.copy(alpha = 0.3f),
            unselectedTextColor = Forest.copy(alpha = 0.3f)
        )
    )
}
