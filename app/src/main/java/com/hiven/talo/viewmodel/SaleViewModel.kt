package com.hiven.talo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.hiven.talo.data.local.entity.ProductEntity
import com.hiven.talo.data.repository.SaleRepository
import com.hiven.talo.ui.screens.sale.CartItemUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SaleViewModel(
    private val saleRepository: SaleRepository
) : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItemUi>>(emptyList())
    val cartItems: StateFlow<List<CartItemUi>> = _cartItems.asStateFlow()

    private val _saleCompleted = MutableStateFlow(false)
    val saleCompleted: StateFlow<Boolean> = _saleCompleted.asStateFlow()

    fun addProductToCart(product: ProductEntity) {
        val currentItems = _cartItems.value.toMutableList()
        val existingIndex = currentItems.indexOfFirst { it.productId == product.id }

        if (existingIndex >= 0) {
            val current = currentItems[existingIndex]
            currentItems[existingIndex] = current.copy(quantity = current.quantity + 1)
        } else {
            currentItems.add(
                CartItemUi(
                    productId = product.id,
                    productName = product.name,
                    unitPrice = product.price,
                    quantity = 1
                )
            )
        }

        _cartItems.value = currentItems
    }

    fun increaseQuantity(productId: Long) {
        _cartItems.value = _cartItems.value.map {
            if (it.productId == productId) it.copy(quantity = it.quantity + 1) else it
        }
    }

    fun decreaseQuantity(productId: Long) {
        _cartItems.value = _cartItems.value.mapNotNull {
            if (it.productId != productId) {
                it
            } else {
                val newQty = it.quantity - 1
                if (newQty <= 0) null else it.copy(quantity = newQty)
            }
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun getTotal(): Double {
        return _cartItems.value.sumOf { it.lineTotal }
    }

    fun confirmCashSale() {
        viewModelScope.launch {
            val currentCart = _cartItems.value
            if (currentCart.isEmpty()) return@launch

            saleRepository.confirmCashSale(currentCart)
            _cartItems.value = emptyList()
            _saleCompleted.value = true
        }
    }

    fun consumeSaleCompleted() {
        _saleCompleted.value = false
    }
}

class SaleViewModelFactory(
    private val saleRepository: SaleRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SaleViewModel(saleRepository) as T
    }
}