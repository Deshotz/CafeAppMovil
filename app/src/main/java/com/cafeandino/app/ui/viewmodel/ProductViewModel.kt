package com.cafeandino.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cafeandino.app.data.local.entity.ProductEntity
import com.cafeandino.app.data.repository.ProductRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    // 📦 Lista de productos desde la base de datos (Room)
    val products: StateFlow<List<ProductEntity>> = repository.getAllProducts()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    // 🛠️ Insertar productos en la base de datos
    fun insertProducts(list: List<ProductEntity>) {
        viewModelScope.launch {
            repository.insertProducts(list)
        }
    }

    // 🧹 Limpiar todos los productos (opcional)
    fun clearProducts() {
        viewModelScope.launch {
            repository.clearProducts()
        }
    }

    // ☕ Precargar productos si la base de datos está vacía
    fun preloadProductsIfEmpty() {
        viewModelScope.launch {
            if (products.value.isEmpty()) {
                val initialProducts = listOf(
                    ProductEntity(
                        id = 1,
                        name = "Café Espresso",
                        description = "☕ Intenso y aromático, el clásico café corto.",
                        price = 8.50,
                        imageUrl = "https://cdn-icons-png.flaticon.com/512/415/415733.png"
                    ),
                    ProductEntity(
                        id = 2,
                        name = "Café Latte",
                        description = "🥛 Café suave con leche espumosa y cremosa.",
                        price = 10.00,
                        imageUrl = "https://cdn-icons-png.flaticon.com/512/590/590836.png"
                    ),
                    ProductEntity(
                        id = 3,
                        name = "Capuccino",
                        description = "🍶 Equilibrio perfecto entre café, leche y espuma.",
                        price = 9.50,
                        imageUrl = "https://cdn-icons-png.flaticon.com/512/2935/2935395.png"
                    ),
                    ProductEntity(
                        id = 4,
                        name = "Mocha",
                        description = "🍫 Café con leche, chocolate y crema batida.",
                        price = 11.00,
                        imageUrl = "https://cdn-icons-png.flaticon.com/512/2972/2972269.png"
                    )
                )
                insertProducts(initialProducts)
            }
        }
    }
}