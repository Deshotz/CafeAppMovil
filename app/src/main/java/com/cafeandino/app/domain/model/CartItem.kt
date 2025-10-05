package com.cafeandino.app.domain.model

/**
 * 🛒 CartItem:
 * Representa un producto dentro del carrito con cantidad.
 */
data class CartItem(
    val productId: Int,
    val name: String,
    val price: Double,
    val quantity: Int
)