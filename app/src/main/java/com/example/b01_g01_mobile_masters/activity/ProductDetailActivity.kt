package com.example.b01_g01_mobile_masters.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.b01_g01_mobile_masters.MainActivity
import com.example.b01_g01_mobile_masters.R
import com.example.b01_g01_mobile_masters.databinding.ActivityProductDetailBinding
import com.example.b01_g01_mobile_masters.roomdb.AppDatabase
import com.example.b01_g01_mobile_masters.roomdb.ProductDao
import com.example.b01_g01_mobile_masters.roomdb.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)

        getProductDetails(intent.getStringExtra("id"))

        setContentView(binding.root)
    }

    private fun getProductDetails(proId: String?) {
        Firebase.firestore.collection("products")
            .document(proId!!).get().addOnSuccessListener {
                val list = it.get("productImages") as ArrayList<String>
                val name = it.getString("productTitle")
                val productPrice = it.getString("productPrice")
                val productDesc = it.getString("productDescription")
                binding.textView7.text = name
                binding.textView8.text = productPrice
                binding.textView9.text = productDesc

                val slideList = ArrayList<SlideModel>()
                for (data in list){
                    slideList.add(SlideModel(data, ScaleTypes.FIT))
                }


                cartAction(proId, name, productPrice, it.getString("productCoverImg"))

                binding.imageSlider.setImageList(slideList)

            }.addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

    }

    private fun cartAction(proId: String, name: String?, productPrice: String?,coverImg: String?) {
        val productDao = AppDatabase.getInstance(this).ProductDao()

        if(productDao.isExit(proId)!=null){
            binding.textView10.text = "Go to cart"
        }else{
            binding.textView10.text = "Add to cart"
        }

        binding.textView10.setOnClickListener{
            if(productDao.isExit(proId)!=null){
                openCart()
            }else{
                addToCart(productDao, proId, name, productPrice, coverImg)
            }
        }

    }

    private fun addToCart(productDao: ProductDao,
                          proId: String,
                          name: String?,
                          productPrice: String?,
                          coverImg: String?) {
        val data = ProductModel(proId,name,coverImg,productPrice)
        lifecycleScope.launch(Dispatchers.IO){
            productDao.insertProduct(data)
            binding.textView10.text = "Go to cart"
        }

    }

    private fun openCart() {
        val preference = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart",true)
        editor.apply()

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}