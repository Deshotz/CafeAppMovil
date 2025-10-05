package com.cafeandino.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cafeandino.app.ui.screen.CartScreen
import com.cafeandino.app.ui.screen.CatalogScreen
import com.cafeandino.app.ui.screen.CheckoutScreen
import com.cafeandino.app.ui.screen.DetailScreen
import com.cafeandino.app.ui.viewmodel.CartViewModel
import com.cafeandino.app.ui.viewmodel.ProductViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    productViewModel: ProductViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "catalog"
    ) {

        composable("catalog") {
            CatalogScreen(
                navController = navController,
                cartViewModel = cartViewModel,
                productViewModel = productViewModel
            )
        }

        composable("cart") {
            CartScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        composable("checkout") {
            CheckoutScreen(
                navController = navController,
                cartViewModel = cartViewModel
            )
        }

        composable("detail/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: -1
            DetailScreen(
                productId = productId,
                navController = navController,
                cartViewModel = cartViewModel,
                productViewModel = productViewModel
            )
        }
    }
}