package com.example.simpleweatherapp.ui

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.simpleweatherapp.R

class FindPlaceDialogFragment : AppCompatDialogFragment() {

    private lateinit var listener : FindPlaceListener
    private lateinit var findPlace: EditText


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.find_place_dialog_fragment, null)
        findPlace = view.findViewById(R.id.et_find_place)
        builder.setView(view)
            .setTitle("Find Place")
            .setNegativeButton("Cancel") { _, _ ->
                dialog?.cancel()
            }
            .setPositiveButton("OK"){ dialogInterface: DialogInterface, i: Int ->
                val place = findPlace.text.toString()
                listener.setPlace(place)
            }
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            targetFragment as FindPlaceListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + "must implement FindDialogListener")
        }
    }


    interface FindPlaceListener{
        fun setPlace(place: String)
    }
}