package com.example.b01_g01_mobile_masters.model

data class AddProductModel(
    val productTitle: String? = "",
    val productDescription: String? = "",
    val productPrice: String? = "",
    val productContact: String? =  "",
    val productAddress:  String? = "",
    val productCoverImg: String? = "",
    val productCategory: String? = "",
    val productId: String? = "",
    val productImages: ArrayList<String> = ArrayList()







)
