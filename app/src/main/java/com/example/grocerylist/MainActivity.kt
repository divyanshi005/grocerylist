package com.example.grocerylist



import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.data.repo.GroceryRepository
import com.example.grocerylist.data.repo.GroceryDatabase
import com.example.grocerylist.data.room.GroceryItems
import com.example.grocerylist.ui.DialogListener
import com.example.grocerylist.ui.GroceryItemDialog
import com.example.grocerylist.ui.GroceryViewModel
import com.example.grocerylist.ui.GroceryViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: GroceryViewModel
    private lateinit var rvList : RecyclerView
    private lateinit var btnAdd : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnAdd = findViewById(R.id.btnAdd)
        rvList = findViewById(R.id.rvList)
        val groceryRepository = GroceryRepository(GroceryDatabase(this))
        val factory = GroceryViewModelFactory(groceryRepository)

        // Initialised View Model
        viewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]
        val groceryAdapter = GroceryAdapter(listOf(), viewModel)
        rvList.layoutManager = LinearLayoutManager(this)
        rvList.adapter = groceryAdapter

        // To display all items in recycler view
        viewModel.allGroceryItems().observe(this) {
            groceryAdapter.list = it
            groceryAdapter.notifyItemInserted(groceryAdapter.list.size)
            groceryAdapter.notifyDataSetChanged()
        }

        // on ClickListener on button to open dialog box
        btnAdd.setOnClickListener {
            GroceryItemDialog(this, object : DialogListener {
                override fun onAddButtonClicked(item: GroceryItems) {
                    viewModel.insert(item)
                }
            }).show()
        }
    }
}