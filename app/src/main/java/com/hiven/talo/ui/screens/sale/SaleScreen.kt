package com.hiven.talo.ui.screens.sale

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hiven.talo.data.local.entity.ProductEntity
import com.hiven.talo.viewmodel.ProductViewModel
import com.hiven.talo.viewmodel.SaleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaleScreen(
    productViewModel: ProductViewModel,
    saleViewModel: SaleViewModel
) {
    val products by productViewModel.products.collectAsState()
    val cartItems by saleViewModel.cartItems.collectAsState()
    val saleCompleted by saleViewModel.saleCompleted.collectAsState()
    val total = saleViewModel.getTotal()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(saleCompleted) {
        if (saleCompleted) {
            snackbarHostState.showSnackbar("Vente enregistrée avec succès")
            saleViewModel.consumeSaleCompleted()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Vente") })
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Produits")

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(products) { product ->
                    ProductRow(
                        product = product,
                        onAddClick = {
                            saleViewModel.addProductToCart(product)
                        }
                    )
                }
            }

            Divider()

            Text("Panier")

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cartItems) { item ->
                    CartRow(
                        item = item,
                        onIncrease = { saleViewModel.increaseQuantity(item.productId) },
                        onDecrease = { saleViewModel.decreaseQuantity(item.productId) }
                    )
                }
            }

            Divider()

            Text("Total : $total FC")

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { saleViewModel.clearCart() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Vider")
                }

                Button(
                    onClick = { saleViewModel.confirmCashSale() },
                    modifier = Modifier.weight(1f),
                    enabled = cartItems.isNotEmpty()
                ) {
                    Text("Payer")
                }
            }
        }
    }
}

@Composable
private fun ProductRow(
    product: ProductEntity,
    onAddClick: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(product.name)
                Text("${product.price} FC | Stock: ${product.stockQuantity}")
            }

            Button(onClick = onAddClick) {
                Text("+")
            }
        }
    }
}

@Composable
private fun CartRow(
    item: CartItemUi,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(item.productName)
            Text("PU: ${item.unitPrice} FC")
            Text("Qté: ${item.quantity}")
            Text("Sous-total: ${item.lineTotal} FC")

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = onDecrease) {
                    Text("-")
                }
                Button(onClick = onIncrease) {
                    Text("+")
                }
            }
        }
    }
}