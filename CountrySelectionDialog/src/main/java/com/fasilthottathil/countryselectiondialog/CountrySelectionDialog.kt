package com.fasilthottathil.countryselectiondialog

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fasilthottathil.countryselectiondialog.models.Country
import com.fasilthottathil.countryselectiondialog.models.CountryItem
import com.google.gson.Gson
import java.lang.RuntimeException

class CountrySelectionDialog {

    companion object{
        private var alertDialog: AlertDialog? = null
        private val countrySelectionAdapter by lazy { CountrySelectionAdapter() }

        fun CountrySelectionDialog.show(): CountrySelectionDialog? {
            return if (alertDialog == null) {
                null
            } else {
                alertDialog?.show()
                this
            }
        }

        private var onCountrySelectedListener:((CountryItem)->Unit)? = null

        fun CountrySelectionDialog.setOnCountrySelected(listener:(CountryItem)->Unit){
            onCountrySelectedListener = listener
        }

    }

    private fun getCountry(context: Context): ArrayList<CountryItem> {
        val jsonString: String = try {
            context.assets.open("country_codes.json").bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            Log.d("IO Exception", e.message.toString())
            ""
        }

        val country: ArrayList<CountryItem> = arrayListOf()
        Gson().fromJson(jsonString, Country::class.java).forEach {
            country.add(it)
        }
        return country
    }

    fun create(context: Context):CountrySelectionDialog {

        alertDialog = AlertDialog.Builder(context)
            .create()

        val view = View.inflate(context, R.layout.country_dialog, null)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvCountry)
        val txtCancel = view.findViewById<TextView>(R.id.txtCancel)
        val edtSearch = view.findViewById<EditText>(R.id.edtSearch)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = countrySelectionAdapter
        }

        countrySelectionAdapter.setData(getCountry(context))

        countrySelectionAdapter.setOnCountrySelectedListener {
            onCountrySelectedListener?.invoke(it)
        }

        edtSearch.addTextChangedListener {
            countrySelectionAdapter.filter.filter(it)
        }

        alertDialog?.apply {
            setView(view)
            setOnCancelListener {
                alertDialog = null
                recyclerView.adapter = null
            }
        }

        txtCancel.setOnClickListener {
            alertDialog?.cancel()
        }
        return this
    }


}