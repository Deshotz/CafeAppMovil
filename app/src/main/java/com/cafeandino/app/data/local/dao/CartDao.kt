package com.cafeandino.app.data.local.dao

import androidx.room.*
import com.cafeandino.app.data.local.entity.CartItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    // 🛒 Obtener todos los productos del carrito
    @Query("SELECT * FROM cart_items ORDER BY id DESC")
    fun observeCart(): Flow<List<CartItemEntity>>

    // 🔍 Buscar un producto específico por su ID (para verificar si ya está en el carrito)
    @Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
    suspend fun findByProductId(productId: Int): CartItemEntity?

    // ➕ Insertar un producto en el carrito
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartItemEntity)

    // ✏️ Actualizar un producto existente
    @Update
    suspend fun update(item: CartItemEntity)

    // ❌ Eliminar un producto del carrito
    @Delete
    suspend fun delete(item: CartItemEntity)

    // 🧹 Vaciar el carrito por completo
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()

    // 💵 Obtener el total del carrito en tiempo real
    @Query("SELECT COALESCE(SUM(quantity * price), 0) FROM cart_items")
    fun observeTotal(): Flow<Double>
}