package com.cafeandino.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.cafeandino.app.ui.viewmodel.CartViewModel
import com.cafeandino.app.ui.viewmodel.ProductViewModel

@Composable
fun DetailScreen(
    productId: Int,
    navController: NavHostController,
    cartViewModel: CartViewModel,
    productViewModel: ProductViewModel
) {
    val products = productViewModel.products.collectAsState()
    val product = products.value.find { it.id == productId }

    if (product == null) {
        Text("Producto no encontrado")
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(product.imageUrl),
            contentDescription = product.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = product.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(text = product.description)
        Text(
            text = "Precio: S/ ${product.price}",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            cartViewModel.addToCart(product)
            navController.navigate("cart")
        }, modifier = Modifier.fillMaxWidth()) {
            Text("🛒 Agregar al carrito")
        }
    }
}