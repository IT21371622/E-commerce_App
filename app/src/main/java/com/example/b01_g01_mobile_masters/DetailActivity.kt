package com.example.addvertisements

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.addvertisements.databinding.ActivityDetailBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DetailActivity : AppCompatActivity() {

    data class Advertisement(
        val title: String = "",
        val description: String = "",
        val type: String = "",
        val price: String = "",
        val contact: String = "",
        val address: String = ""
    )

    private lateinit var binding: ActivityDetailBinding
    private lateinit var firebaseRef: DatabaseReference
    private var advertisementId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseRef = FirebaseDatabase.getInstance().getReference("Advertisements")

        val bundle = intent.extras
        if (bundle != null) {
            advertisementId = bundle.getString("Id")
           // binding.detailId.text = bundle.getString("Id")?.toEditable()
            binding.detailDescription.text = bundle.getString("Description")?.toEditable()
            binding.detailTitle.text = bundle.getString("Title")?.toEditable()
            binding.detailType.text = bundle.getString("Type")?.toEditable()
            binding.detailPrice.text = bundle.getString("Price")?.toEditable()
            binding.detailContact.text = bundle.getString("Contact")?.toEditable()
            binding.detailAddress.text = bundle.getString("Address")?.toEditable()
            Glide.with(this).load(bundle.getString("Image")).into(binding.detailImage)


        }

        binding.mainUpdate.setOnClickListener {

            updateAdvertisement()
        }

        binding.mainDelete.setOnClickListener {
            val intent = Intent(this@DetailActivity, DeleteActivity::class.java)
            intent.putExtra("2", advertisementId)
            startActivity(intent)
        }
    }

    //update
    private fun updateAdvertisement() {
       // val id = binding.detailId.text.toString().trim()
        val title = binding.detailTitle.text.toString().trim()
        val description = binding.detailDescription.text.toString().trim()
        val type = binding.detailType.text.toString().trim()
        val price = binding.detailPrice.text.toString().trim()
        val contact = binding.detailContact.text.toString().trim()
        val address = binding.detailAddress.text.toString().trim()


        if (title.isEmpty() || description.isEmpty() || type.isEmpty() || price.isEmpty() || contact.isEmpty() || address.isEmpty()) {
            // Show an error message if any of the fields are empty
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        // Update the data in the Firebase database
        val advertisement = Advertisement(title, description, type, price, contact, address)

        firebaseRef.child("3").setValue(advertisement).addOnSuccessListener {
            Toast.makeText(this, "Advertisement updated", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to update advertisement", Toast.LENGTH_SHORT).show()
        }
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}