package com.codekul.notekeeping

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.SearchView
import com.codekul.notekeeping.R.id.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_create.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.note_card.*
import java.io.FileNotFoundException
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v4.view.MenuItemCompat.getActionView
import android.content.Context.SEARCH_SERVICE
import android.app.SearchManager
import android.content.Context


class MainActivity : AppCompatActivity(), ItemRowListener{
    override fun onItemTouched(title: String, data: String?) {
            Log.i("@yog","card_onClick")
            val intent = Intent(this,CreateActivity::class.java)
            val bndl = Bundle()
            bndl.putString("filename","$title")
            intent.putExtras(bndl)
            startActivityForResult(intent, 123)

    }

    var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    var rawData : ArrayList<note> = ArrayList()
    final private val RESULT_CODE = 11;
    var adapt: Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            var  i = Intent(this,CreateActivity::class.java)
            startActivity(i)
        }
        mSwipeRefreshLayout = swipe_container as SwipeRefreshLayout?
        mSwipeRefreshLayout?.setOnRefreshListener { updateList() }
        mSwipeRefreshLayout?.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark)
//        /**
//         * Showing Swipe Refresh animation on activity create
//         * As animation won't start on onCreate, post runnable is used
//         */
//        mSwipeRefreshLayout?.post {
//            mSwipeRefreshLayout?.isRefreshing = true
//        }


        val recyclerView = findViewById(R.id.cardview_note) as RecyclerView
        recyclerView.layoutManager = GridLayoutManager(this,2)
        updateList()
        adapt= Adapter(this,rawData)
        recyclerView.adapter = adapt




    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_view, menu)

//        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
//        val searchView = menu.findItem(R.id.search_view).actionView as SearchView
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(componentName))

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_view -> {
                val recyclerView = findViewById(R.id.cardview_note) as RecyclerView
                recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                recyclerView.adapter = adapt

                return true
            }
            R.id.search_view -> {

                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            RESULT_CODE -> if (resultCode == Activity.RESULT_OK) {
                updateList()
            }
        }
    }


    override fun onResume() {
        updateList()
        super.onResume()
    }

    override fun onRestart() {
        updateList()
        super.onRestart()
    }

    override fun onBackPressed() {
       updateList()

        super.onBackPressed()
    }

    fun  updateList(){
        mSwipeRefreshLayout?.isRefreshing =true
        rawData.clear()
        filesDir.list().forEach {
           try {
               if(it.isNotBlank()) {
                   val dtreceived = retrieve("${it.removeSuffix(".txt")}")
                   rawData.add(note("${it.removeSuffix(".txt")}",dtreceived))
               }
           }catch(e : FileNotFoundException){
               null
           }
        }

        adapt?.notifyDataSetChanged()
        mSwipeRefreshLayout?.isRefreshing = false;
    }



    fun retrieve(filename: String): String? {
        val isp = openFileInput("$filename.txt")
        val dt = isp.bufferedReader().use {
            it.readLine()
        }
        return dt
    }
}

