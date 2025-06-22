package com.example.grocerylist


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.grocerylist.data.room.GroceryItems
import com.example.grocerylist.ui.GroceryViewModel

class GroceryAdapter(var list: List<GroceryItems>, private val viewModel: GroceryViewModel) :
    RecyclerView.Adapter<GroceryAdapter.GroceryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grocery, parent, false)
        return GroceryViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {
        val currentPosition = list[position]
        holder.txtItemName.text = currentPosition.itemName.lowercase()
            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

        holder.txtItemPrice.text = "Price: ₹${currentPosition.itemPrice}"
        holder.txtItemQuantity.text = "Quantity: ${currentPosition.itemQuantity}"
        Glide.with(holder.itemView.context).load(
            when(currentPosition.itemName.trim().lowercase()) {
                "banana" -> R.drawable.banana
                "kiwi" -> R.drawable.kiwi
                "apple" -> R.drawable.apple
                "orange" -> R.drawable.orange
                "potato" -> R.drawable.potato
                else -> R.drawable.vegetables
            }
        ).into(holder.imgItemImage)

        holder.ibDelete.setOnClickListener {
            viewModel.delete(currentPosition)
        }

        // Show total cost and title only for the last item
        if (position == list.size - 1) {
            val totalCost = list.sumOf { it.itemPrice }
            holder.txtItemTotalCost.visibility = View.VISIBLE
            holder.txtTotalCostTitle.visibility = View.VISIBLE
            holder.txtItemTotalCost.text = "₹$totalCost"
        } else {
            // Hide the total cost and title for non-last items
            holder.txtItemTotalCost.visibility = View.GONE
            holder.txtTotalCostTitle.visibility = View.GONE
        }
    }

    // Inner class for ViewHolder with findViewById
    inner class GroceryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtItemName: TextView = itemView.findViewById(R.id.txtItemName)
        val txtItemPrice: TextView = itemView.findViewById(R.id.txtItemPrice)
        val txtItemQuantity: TextView = itemView.findViewById(R.id.txtItemQuantity)
        val txtItemTotalCost: TextView = itemView.findViewById(R.id.txtItemTotalCost)
        val txtTotalCostTitle: TextView = itemView.findViewById(R.id.txtTotalCostTitle)
        val imgItemImage: ImageView = itemView.findViewById(R.id.imgItemImage)
        val ibDelete: ImageButton = itemView.findViewById(R.id.ibDelete)
    }
}