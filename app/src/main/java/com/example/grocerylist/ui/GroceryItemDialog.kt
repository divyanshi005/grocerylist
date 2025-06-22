package com.example.grocerylist.ui


import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.example.grocerylist.data.room.GroceryItems
import com.example.grocerylist.R

class GroceryItemDialog(context: Context, private var dialogListener: DialogListener) : AppCompatDialog(context) {
    private lateinit var etItemName : EditText
    private lateinit var etItemQuantity : EditText
    private lateinit var etItemPrice : EditText
    private lateinit var tvSave : TextView
    private lateinit var tvCancel : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.grocerydialog)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        etItemName = findViewById(R.id.etItemName)!!
        etItemQuantity = findViewById(R.id.etItemQuantity)!!
        etItemPrice = findViewById(R.id.etItemPrice)!!
        tvSave = findViewById(R.id.tvSave)!!
        tvCancel = findViewById(R.id.tvCancel)!!

        // Click listener on Save button
        // to save all data.
        tvSave.setOnClickListener {

            // Take all three inputs in different variables from user
            // and add it in Grocery Items database
            val name = etItemName.text.toString()
            val quantity = etItemQuantity.text.toString().toInt()
            val price = etItemPrice.text.toString().toInt()

            // Toast to display enter items in edit text
            if (name.isEmpty()) {
                Toast.makeText(context, "Please Enter Item Name", Toast.LENGTH_SHORT).show()
            }

            val item = GroceryItems(name, quantity, price)
            dialogListener.onAddButtonClicked(item)
            dismiss()
        }

        // On click listener on cancel text to close dialog box
        tvCancel.setOnClickListener {
            cancel()
        }
    }
}