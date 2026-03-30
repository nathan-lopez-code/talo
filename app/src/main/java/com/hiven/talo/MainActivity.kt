package com.hiven.talo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hiven.talo.data.local.database.DatabaseProvider
import com.hiven.talo.data.repository.ProductRepository
import com.hiven.talo.ui.navigation.Screen
import com.hiven.talo.ui.screens.home.HomeScreen
import com.hiven.talo.ui.screens.stock.StockScreen
import com.hiven.talo.ui.theme.TaloTheme
import com.hiven.talo.viewmodel.ProductViewModel
import com.hiven.talo.viewmodel.ProductViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation DB + Repository
        val db = DatabaseProvider.getDatabase(this)
        val productRepository = ProductRepository(db.productDao())
        val factory = ProductViewModelFactory(productRepository)

        setContent {
            TaloTheme {
                AppNavigation(factory)
            }
        }
    }
}

@Composable
fun AppNavigation(
    factory: ProductViewModelFactory
) {
    val navController = rememberNavController()

    val productViewModel: ProductViewModel = viewModel(factory = factory)

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        // Accueil
        composable(Screen.Home.route) {
            HomeScreen(
                onStockClick = {
                    navController.navigate(Screen.Stock.route)
                }
            )
        }

        // Stock
        composable(Screen.Stock.route) {
            StockScreen(viewModel = productViewModel)
        }
    }
}