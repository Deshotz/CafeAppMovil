package com.cafeandino.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.cafeandino.app.data.local.db.AppDatabase
import com.cafeandino.app.data.repository.ProductRepository
import com.cafeandino.app.ui.navigation.AppNavGraph
import com.cafeandino.app.ui.theme.CafeAndinoTheme
import com.cafeandino.app.ui.viewmodel.CartViewModel
import com.cafeandino.app.ui.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Inicializar base de datos y DAOs
        val db = AppDatabase.get(applicationContext)
        val productDao = db.productDao()
        val cartDao = db.cartDao()

        // ✅ Crear repositorios y ViewModels
        val productRepository = ProductRepository(productDao)
        val productViewModel = ProductViewModel(productRepository)
        val cartViewModel = CartViewModel(cartDao)

        // ✅ Precargar productos si la BD está vacía
        productViewModel.preloadProductsIfEmpty()

        // ✅ Cargar interfaz de usuario
        setContent {
            CafeAndinoTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    AppNavGraph(
                        navController = navController,
                        cartViewModel = cartViewModel,
                        productViewModel = productViewModel
                    )
                }
            }
        }
    }
}