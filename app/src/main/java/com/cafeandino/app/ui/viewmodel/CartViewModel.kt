package com.cafeandino.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeandino.app.data.local.dao.CartDao
import com.cafeandino.app.data.local.entity.CartItemEntity
import com.cafeandino.app.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CartViewModel(private val cartDao: CartDao) : ViewModel() {

    val cartItems: Flow<List<CartItemEntity>> = cartDao.observeCart()
    val total: Flow<Double> = cartDao.observeTotal()

    fun addToCart(product: ProductEntity) {
        viewModelScope.launch {
            val existing = cartDao.findByProductId(product.id)
            if (existing != null) {
                cartDao.update(existing.copy(quantity = existing.quantity + 1))
            } else {
                cartDao.insert(
                    CartItemEntity(
                        productId = product.id,
                        name = product.name,
                        price = product.price,
                        quantity = 1
                    )
                )
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            cartDao.clearCart()
        }
    }
}