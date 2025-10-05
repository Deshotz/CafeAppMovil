package com.cafeandino.app.domain.model

/**
 * 📦 Product:
 * Modelo que representa un producto en la app.
 */
data class Product(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String
)
