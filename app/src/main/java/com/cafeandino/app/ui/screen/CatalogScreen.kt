package com.cafeandino.app.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.cafeandino.app.ui.viewmodel.CartViewModel
import com.cafeandino.app.ui.viewmodel.ProductViewModel

@Composable
fun CatalogScreen(
    navController: NavHostController,
    cartViewModel: CartViewModel,
    productViewModel: ProductViewModel
) {
    // 📦 Obtenemos productos desde la base de datos (Room)
    val products by productViewModel.products.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "☕ Catálogo de productos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // 🛠️ Si no hay productos, mostrar mensaje
        if (products.isEmpty()) {
            Text(
                text = "⚠️ No hay productos disponibles.\nAgrega algunos desde la base de datos.",
                style = MaterialTheme.typography.bodyLarge
            )
        } else {
            // 📜 Lista de productos desde Room
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(products) { product ->
                    Card(
                        elevation = CardDefaults.cardElevation(4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("detail/${product.id}")
                            }
                    ) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Image(
                                painter = rememberAsyncImagePainter(product.imageUrl),
                                contentDescription = product.name,
                                modifier = Modifier
                                    .size(90.dp)
                                    .padding(end = 16.dp),
                                contentScale = ContentScale.Crop
                            )

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = product.name,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = product.description,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "S/ ${product.price}",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.SemiBold
                                )

                                Spacer(modifier = Modifier.height(8.dp))

                                Button(onClick = { cartViewModel.addToCart(product) }) {
                                    Text("Agregar al carrito 🛒")
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🛒 Ir al carrito
        Button(
            onClick = { navController.navigate("cart") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🛍️ Ver carrito de compras")
        }
    }
}