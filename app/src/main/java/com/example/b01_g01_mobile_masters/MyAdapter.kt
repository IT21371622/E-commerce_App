package com.example.addvertisements
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(private val context: android.content.Context, private var dataList: List<DataClass>): RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = dataList[position]
        Glide.with(context).load(data.image).into(holder.recImage)
        holder.recTitle.text = data.title
        holder.recType.text = data.type
        holder.recDescription.text = data.description
        holder.recContact.text = data.contact
        holder.recPrice.text = data.price
        //holder.recPrice.text = data.price?.toString()   // Convert the Double to a String for display
        holder.recAddress.text = data.address

        holder.recCard.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("Image", data.image)
            intent.putExtra("Type", data.type)
            intent.putExtra("Title", data.title)
            intent.putExtra("Description", data.description)
            intent.putExtra("Contact", data.contact)
            intent.putExtra("Price", data.price)
            intent.putExtra("Address", data.address)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun searchDataList(searchList: List<DataClass>) {
        dataList = searchList
        notifyDataSetChanged()
    }
}


class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val recPrice: TextView = itemView.findViewById(R.id.recPrice)
    var recContact: TextView = itemView.findViewById(R.id.recContact)
    var recAddress: TextView = itemView.findViewById(R.id.recAddress)
    var recImage: ImageView = itemView.findViewById(R.id.recImage)
    var recTitle: TextView = itemView.findViewById(R.id.recTitle)
    var recType: TextView = itemView.findViewById(R.id.recType)
    var recDescription: TextView = itemView.findViewById(R.id.recDescription)
    var recCard: CardView = itemView.findViewById(R.id.recCard)
}
