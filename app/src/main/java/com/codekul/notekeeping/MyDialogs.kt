package com.codekul.notekeeping


import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MyDialogs : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        when (tag) {
            "FileBlank" -> {
                return AlertDialog.Builder(activity as Context)
                        .setTitle("Title")
                        .setMessage("MESSAGE")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("Okay",
                                { dialog, which -> Log.i("@MSG", "okay") })
                        .setNegativeButton("Cancel",
                                { dialog, which -> Log.i("@MSG", "Cancel") }).create()
            }
        }

            return Dialog(activity as Context)

        }

    }



