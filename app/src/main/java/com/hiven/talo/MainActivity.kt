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
import com.hiven.talo.data.repository.SaleRepository
import com.hiven.talo.ui.navigation.Screen
import com.hiven.talo.ui.screens.home.HomeScreen
import com.hiven.talo.ui.screens.sale.SaleScreen
import com.hiven.talo.ui.screens.stock.StockScreen
import com.hiven.talo.ui.theme.TaloTheme
import com.hiven.talo.viewmodel.ProductViewModel
import com.hiven.talo.viewmodel.ProductViewModelFactory
import com.hiven.talo.viewmodel.SaleViewModel
import com.hiven.talo.viewmodel.SaleViewModelFactory

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = DatabaseProvider.getDatabase(this)

        val productRepository = ProductRepository(db.productDao())
        val saleRepository = SaleRepository(
            saleDao = db.saleDao(),
            productDao = db.productDao()
        )

        val productFactory = ProductViewModelFactory(productRepository)
        val saleFactory = SaleViewModelFactory(saleRepository)

        setContent {
            TaloTheme {
                AppNavigation(
                    productFactory = productFactory,
                    saleFactory = saleFactory
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    productFactory: ProductViewModelFactory,
    saleFactory: SaleViewModelFactory
) {
    val navController = rememberNavController()

    val productViewModel: ProductViewModel = viewModel(factory = productFactory)
    val saleViewModel: SaleViewModel = viewModel(factory = saleFactory)

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onSaleClick = {
                    navController.navigate(Screen.Sale.route)
                },
                onStockClick = {
                    navController.navigate(Screen.Stock.route)
                }
            )
        }

        composable(Screen.Stock.route) {
            StockScreen(viewModel = productViewModel)
        }

        composable(Screen.Sale.route) {
            SaleScreen(
                productViewModel = productViewModel,
                saleViewModel = saleViewModel
            )
        }
    }
}