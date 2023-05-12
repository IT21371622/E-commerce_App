package com.example.mad4



import DataClass
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView



class MyAdapter(private val context:android.content.Context, private var dataList:List<DataClass>): RecyclerView.Adapter<MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent , false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.recName.text = dataList[position].dataName
        holder.recComment.text = dataList[position].dataComment



        holder.recCard.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("Image", dataList[holder.adapterPosition].dataImage)
            intent.putExtra("Comment", dataList[holder.adapterPosition].dataComment)
            intent.putExtra("Name", dataList[holder.adapterPosition].dataName)

            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var recName: TextView
    var recComment: TextView

    var recCard: CardView

    init {
        recName = itemView.findViewById(R.id.recName)
        recComment = itemView.findViewById(R.id.recComment)

        recCard = itemView.findViewById(R.id.recCard)
    }
}