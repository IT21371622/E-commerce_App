package com.example.mad4

import DataClass
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.mad4.databinding.ActivityUploadBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.DateFormat
import java.util.Calendar

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private var imageURL: String? = null
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Activity result launcher to handle the result of selecting an image
        val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                uri = data?.data
                binding.uploadImage.setImageURI(uri)
            } else {
                Toast.makeText(this@UploadActivity, "No image selected", Toast.LENGTH_SHORT).show()
            }
        }

        // Open the image picker when the upload image is clicked
        binding.uploadImage.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            activityResultLauncher.launch(photoPicker)
        }

        // Save data when the submit button is clicked
        binding.submitButton.setOnClickListener {
            if (validateForm()) {
                saveData()
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun saveData() {
        val storageReference = FirebaseStorage.getInstance().reference.child("Task images")
            .child(uri!!.lastPathSegment!!)

        val builder = AlertDialog.Builder(this@UploadActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while (!uriTask.isComplete);
            val urlImage = uriTask.result
            imageURL = urlImage.toString()
            uploadData()
            dialog.dismiss()
        }.addOnFailureListener {
            dialog.dismiss()
            Toast.makeText(this@UploadActivity, "Failed to upload image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun uploadData() {
        val name = binding.name.text.toString()
        val email = binding.email.text.toString()
        val comment = binding.comment.text.toString()

        val dataClass = DataClass(name, email, comment, imageURL)
        val currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)

        FirebaseDatabase.getInstance().getReference("Advertisements").child(currentDate)
            .setValue(dataClass).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@UploadActivity, "Data saved successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@UploadActivity, "Failed to save data", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { e ->
                Toast.makeText(this@UploadActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
            }
    }

    private fun validateForm(): Boolean {
        // Get the values from the input fields
        val name = binding.name.text.toString()
        val email = binding.email.text.toString()
        val comment = binding.comment.text.toString()

        // Validate the form inputs
        if (name.isEmpty()) {
            binding.name.error = "Name is required"
            return false
        }

        if (email.isEmpty()) {
            binding.email.error = "Email is required"
            return false
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.email.error = "Invalid email address"
            return false
        }

        if (comment.isEmpty()) {
            binding.comment.error = "Comment is required"
            return false
        }

        if (uri == null) {
            Toast.makeText(this@UploadActivity, "Please select an image", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}

