package com.example.grocerylist.ui
import com.example.grocerylist.data.room.GroceryItems

interface DialogListener {

    // Create a function to add items
    // in GroceryItems on clicking
    fun onAddButtonClicked(item: GroceryItems)
}