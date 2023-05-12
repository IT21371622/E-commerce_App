package com.example.b01_g01_mobile_masters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import DataClass
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mad4.MyAdapter
import com.example.mad4.UploadActivity
import com.example.mad4.databinding.ActivityMainBinding
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var datalist: ArrayList<DataClass>
    private lateinit var adapter: MyAdapter
    var databaseReference: DatabaseReference? = null
    var eventListener: ValueEventListener? = null
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gridLayoutManager = GridLayoutManager(this@MainActivity, 1)
        binding.recyclerView.layoutManager = gridLayoutManager

        datalist = ArrayList()
        adapter = MyAdapter(this@MainActivity, datalist)
        binding.recyclerView.adapter = adapter

        // Get a reference to the "Advertisements" node in the Firebase Realtime Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Advertisements")

        // Create and show a progress dialog while retrieving data from the database
        val builder = AlertDialog.Builder(this).setCancelable(false)
        builder.setView(R.layout.progress_layout)
        dialog = builder.create()
        dialog.show()

        binding.fab.setOnClickListener {
            // Open the UploadActivity when the FloatingActionButton is clicked
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }

        // Function to retrieve data from the database and populate the datalist
        fun retrieveData() {
            eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    datalist.clear()
                    for (itemSnapshot in snapshot.children) {
                        val dataClass = itemSnapshot.getValue(DataClass::class.java)
                        dataClass?.let {
                            datalist.add(dataClass)
                        }
                    }
                    adapter.notifyDataSetChanged()
                    dialog.dismiss()
                }

                override fun onCancelled(error: DatabaseError) {
                    dialog.dismiss()
                }
            })
        }

        // Retrieve data when the activity is created
        eventListener = databaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                datalist.clear()
                for (itemSnapshot in snapshot.children) {
                    val dataClass = itemSnapshot.getValue(DataClass::class.java)
                    if (dataClass != null) {
                        datalist.add(dataClass)
                    }
                }
                adapter.notifyDataSetChanged()
                dialog.dismiss()
            }

            override fun onCancelled(error: DatabaseError) {
                dialog.dismiss()
            }
        })

        // Open the UploadActivity when the FloatingActionButton is clicked
        binding.fab.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivity, UploadActivity::class.java)
            startActivity(intent)
        })

        binding.fab.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            startActivity(intent)
        }
    }
}
