package com.example.simpletodo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class editItemActivity: AppCompatActivity() {
//    lateinit var etItem:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edititem)

        val etItem = findViewById<EditText>(R.id.editItem)
        val data = Intent()
        etItem.setText(getIntent().getStringExtra("itemName"))

        findViewById<Button>(R.id.updateBtn).setOnClickListener {
            // Pass relevant data back as a result
            data.putExtra("updatedName", etItem.getText().toString())
            data.putExtra("index", getIntent().getStringExtra("index"))
            data.putExtra("code", 39)
            // Activity finished ok, return the data
            setResult(RESULT_OK, data) // set result code and bundle data for response
            finish() // closes the activity, pass data to parent
        }
    }
}
