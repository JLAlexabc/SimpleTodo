package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.FileUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileSystem
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.text.FieldPosition

class MainActivity : AppCompatActivity() {
//    int REQUEST_CODE = 20
//
//    // FirstActivity, launching an activity for a result
//    fun onClick(view: View) {
//        val i = Intent(this@ActivityOne, ActivityNamePrompt::class.java)
//        i.putExtra("mode", 2) // pass arbitrary data to launched activity
//        startActivityForResult(i, REQUEST_CODE)
//    }
    var listOfTasks = mutableListOf<String>()
    lateinit var adapter:TaskItemAdapter
    val REQUEST_CODE = 39

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inputTextField = findViewById<EditText>(R.id.addTaskField)
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                //1. remove the item from list
                listOfTasks.removeAt(position)
                saveItems()//save into file
                //2. update the adapter the data changed
                adapter.notifyDataSetChanged()
            }
        }
        //set up to the next screen when item be clicked
        val onClickListener = object :TaskItemAdapter.ClickListener{
            override fun onItemClicked(position: Int) {
                val i = Intent(this@MainActivity,editItemActivity::class.java)
                i.putExtra("itemName", listOfTasks[position]) // pass arbitrary data to launched activity
                i.putExtra("index",position)
                startActivityForResult(i, REQUEST_CODE)
            }

        }

        loadItems() //read data from file and save into listOfTasks

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listOfTasks,onLongClickListener,onClickListener)
        //conect with adapter
        recyclerView.adapter=adapter
        recyclerView.layoutManager=LinearLayoutManager(this)

        //user click the button
        findViewById<Button>(R.id.button).setOnClickListener{
            //1. get the text user input @id/addTaskField
            val userInputTask = inputTextField.text.toString()

            //2. add the input string to the tasks listOfTask
            listOfTasks.add(userInputTask)
            saveItems()//save into file

            //notify the adapter that data be updated
            adapter.notifyItemInserted(listOfTasks.size-1)

            //3. clear out the input field
            inputTextField.setText("")


        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            val updateName = data?.getExtras()?.getString("updatedName")
            val code = data?.getExtras()?.getInt("code", 0)
            val index = data?.getExtras()?.getInt("index")
            // need update item to its new name here
            listOfTasks[index!!]=updateName!!
            //save into file, also notify adapter data changed
            adapter.notifyDataSetChanged()
            saveItems()
        }
    }
    //1. save data into file
    fun saveItems(){
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(),listOfTasks)
        }catch (ioException:IOException){
            ioException.printStackTrace()
        }

    }
    //2.get file object
    fun getDataFile(): File {
        return File(filesDir,"data.txt")
    }
    //3. read the data from file
    fun loadItems(){
        try {
            listOfTasks=org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }
}