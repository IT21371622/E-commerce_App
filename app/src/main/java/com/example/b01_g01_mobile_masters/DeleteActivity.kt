package com.example.addvertisements

import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.addvertisements.databinding.ActivityDeleteBinding
import com.example.addvertisements.databinding.ActivityDetailBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deleteButton.setOnClickListener{
            val id = binding.deleteTitle.text.toString()
            Log.d("DeleteActivity", "Title: $title")
            if(title.isNotEmpty()){
                deleteData("3")
            }else{
                Toast.makeText(this,"Please enter phone number",Toast.LENGTH_SHORT).show()
            }
        }
    }

    //delete data by title
    private fun deleteData(title: String){
        Log.d("DeleteActivity", "deleteData() called")
        databaseReference = FirebaseDatabase.getInstance().getReference("Advertisements")
        databaseReference.child(title).removeValue().addOnSuccessListener {
            binding.deleteTitle.text.clear()
            Toast.makeText(this,"Deleted", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener{
            Toast.makeText(this,"Unable to delete", Toast.LENGTH_SHORT).show()
        }
    }

}