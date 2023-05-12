package com.example.mad4

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mad4.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    var imageUrl = ""
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve data passed from the previous activity through intent extras
        val bundle = intent.extras
        if (bundle != null) {
            // Set the comment, name, and email values from the intent extras to the respective TextViews
            binding.detailComment.text = bundle.getString("Comment")
            binding.detailName.text = bundle.getString("Name")
            binding.detailEmail.text = bundle.getString("Email")

            // Load the image from the intent extras using Glide library and display it in the ImageView
            Glide.with(this).load(bundle.getString("Image")).into(binding.detailImage)
        }
    }
}
