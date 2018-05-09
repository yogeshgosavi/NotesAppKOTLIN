package com.codekul.notekeeping

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    var rawDT : ArrayList<String> = ArrayList()
    final private val RESULT_CODE = 11;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            var  i = Intent(this,CreateActivity::class.java)
            startActivity(i)
        }

        filesDir.list().forEach {
            rawDT.add(it.removeSuffix(".txt"))
        }

        var adapter = ArrayAdapter<String>(this@MainActivity,android.R.layout.simple_list_item_1,rawDT)
        ListFiles.adapter = adapter

    ListFiles.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,CreateActivity::class.java)
            val bndl = Bundle()
            bndl.putString("filename","${rawDT.get(position)}")
            intent.putExtras(bndl)
            startActivityForResult(intent, 123)
            Toast.makeText(this,"note touched is : "+rawDT.get(position) , Toast.LENGTH_LONG).show()
        } // "_" to mute veriable if we are not using veriabl;e just mute it kotlin will handle other things

        ListFiles.setOnItemLongClickListener { parent, view, position, id ->
            var file = File("$filesDir/${rawDT.get(position)}.txt")
            file.delete()
            Toast.makeText(this,"removed note : "+rawDT.get(position) , Toast.LENGTH_LONG).show()
            adapter.remove(adapter.getItem(position))
            true }

     //       (ListFiles.adapter as ArrayAdapter<String>).notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RESULT_CODE -> if (resultCode == Activity.RESULT_OK) {
                (ListFiles.adapter as ArrayAdapter<String>).notifyDataSetChanged() //if ui is not getting updated use this
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRestart() {
        super.onRestart()
    }





    }





