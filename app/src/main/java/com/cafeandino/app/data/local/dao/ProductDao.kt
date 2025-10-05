package com.cafeandino.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cafeandino.app.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM products ORDER BY name")
    fun observeProducts(): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("DELETE FROM products")
    suspend fun clear()
}