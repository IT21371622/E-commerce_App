package com.example.b01_g01_mobile_masters.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.annotation.NonNull
import androidx.room.ColumnInfo


@Entity(tableName = "products")
data class ProductModel(
    @PrimaryKey
    @NonNull
    val productId : String,
    @ColumnInfo(name = "productTitle")
    val productTitle : String? = "",
    @ColumnInfo(name = "productImage")
    val productImage : String? = "",
    @ColumnInfo(name = "productTPrice")
    val productPrice : String? = "",


)
