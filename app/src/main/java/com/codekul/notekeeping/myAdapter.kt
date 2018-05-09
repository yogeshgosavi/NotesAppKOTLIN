package com.codekul.notekeeping

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.my_item.view.*

/**
 * Created by YOG on 5/9/2018.
 */

data class MyItem(
        val txt: String
)
class myAdapter (
        private val context: Context,
        private val rawData : ArrayList<MyItem> // " * " means any type
): BaseAdapter() {
    private val inflater : LayoutInflater by lazy {
        LayoutInflater.from(context)
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vw= inflater.inflate(
                R.layout.my_item,
                parent,
                false
        )

        vw.txt.text = rawData[position].txt

        return vw
    }

    override fun getItem(position: Int): Any {
        return rawData[position]
    }

    override fun getItemId(position: Int): Long {
        return  (position*9).toLong()
    }

    override fun getCount(): Int {
        return  rawData.size
    }
}
