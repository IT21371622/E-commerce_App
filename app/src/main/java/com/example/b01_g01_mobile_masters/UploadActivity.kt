package com.example.addvertisements

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.addvertisements.databinding.ActivityUploadBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.DateFormat
import java.util.Calendar
import java.util.Random

class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    var imageURL: String? = null
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
            ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                val data = result.data
                uri = data!!.data
                binding.uploadImage.setImageURI(uri)
            }else{
                Toast.makeText(this@UploadActivity, "No image selected", Toast.LENGTH_SHORT).show()

            }
        }
        binding.uploadImage.setOnClickListener{
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/+"
            activityResultLauncher.launch(photoPicker)
        }
        binding.saveButton.setOnClickListener{
            saveData()
        }
    }
    @SuppressLint("SuspiciousIndentation")
    private fun saveData(){
        val storageReference = FirebaseStorage.getInstance().reference.child("Task images")
            .child(uri!!.lastPathSegment!!)

        val builder = AlertDialog.Builder(this@UploadActivity)
        builder.setCancelable(false)
        builder.setView(R.layout.progress_layout)
        val dialog = builder.create()
        dialog.show()

        storageReference.putFile(uri!!).addOnSuccessListener { taskSnapshot ->
            val uriTask = taskSnapshot.storage.downloadUrl
            while(!uriTask.isComplete);
                val urlImage = uriTask.result
            imageURL = urlImage.toString()
            uploadData()
            dialog.dismiss()
        }.addOnFailureListener{
            dialog.dismiss()
        }
    }
//
//    private fun uploadData(){
//        val title = binding.uploadTitle.text.toString()
//        val type = binding.uploadType.text.toString()
//        val description = binding.uploadDescription.text.toString()
//        val price = binding.uploadPrice.text.toString()
//        val contact = binding.uploadContact.text.toString()
//        val address = binding.uploadAddress.text.toString()
//
//
//        val dataClass = DataClass(title, type, description, imageURL,price,contact,address)
//        val currentDate = DateFormat.getDateTimeInstance().format(Calendar.getInstance().time)
//
//
//
//        //FirebaseDatabase.getInstance().getReference("Advertisements")
//             FirebaseDatabase.getInstance().getReference("Advertisements").child(currentDate)
//            .setValue(dataClass).addOnCompleteListener{task ->
//                if(task.isSuccessful){
//                    Toast.makeText(this@UploadActivity, "saved", Toast.LENGTH_SHORT).show()
//                    finish()
//                }
//            }.addOnFailureListener{e ->
//                Toast.makeText(this@UploadActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
//            }
//    }


    //dataupload
    private fun uploadData(){
        //val id = binding.uploadId.text.toString()
        val title = binding.uploadTitle.text.toString()
        val type = binding.uploadType.text.toString()
        val description = binding.uploadDescription.text.toString()
        val price = binding.uploadPrice.text.toString()
        //val price = binding.uploadPrice.text.toString().toDoubleOrNull()
        val contact = binding.uploadContact.text.toString()
        val address = binding.uploadAddress.text.toString()


        val dataClass = DataClass( title, type, description, imageURL, price, contact, address)

        val databaseReference = FirebaseDatabase.getInstance().getReference("Advertisements")

        databaseReference.child("3").setValue(dataClass).addOnCompleteListener{task ->
            if(task.isSuccessful){
                Toast.makeText(this@UploadActivity, "saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }.addOnFailureListener{e ->
            Toast.makeText(this@UploadActivity, e.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

}