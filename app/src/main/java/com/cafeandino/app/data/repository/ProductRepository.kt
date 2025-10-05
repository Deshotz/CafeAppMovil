package com.cafeandino.app.data.repository

import com.cafeandino.app.data.local.dao.ProductDao
import com.cafeandino.app.data.local.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    fun getAllProducts(): Flow<List<ProductEntity>> = productDao.observeProducts()

    suspend fun insertProducts(products: List<ProductEntity>) {
        productDao.insertAll(products)
    }

    suspend fun clearProducts() {
        productDao.clear()
    }
}