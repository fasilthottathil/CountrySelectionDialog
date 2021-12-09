package com.fasilthottathil.countryselectiondialog.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fasilthottathil.countryselectiondialog.CountrySelectionDialog
import com.fasilthottathil.countryselectiondialog.CountrySelectionDialog.Companion.setOnCountrySelected
import com.fasilthottathil.countryselectiondialog.CountrySelectionDialog.Companion.show
import com.fasilthottathil.countryselectiondialog.R
import com.fasilthottathil.countryselectiondialog.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object{
        const val COUNTRY_NAME = "COUNTRY_NAME"
        const val COUNTRY_CODE = "COUNTRY_CODE"
        const val COUNTRY_DIAL_CODE = "COUNTRY_DIAL_CODE"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnShow.setOnClickListener {
            CountrySelectionDialog().create(this)
                .show()?.setOnCountrySelected {
                    Log.d(COUNTRY_NAME,it.name)
                    Log.d(COUNTRY_CODE,it.code)
                    Log.d(COUNTRY_DIAL_CODE,it.dial_code)
                }
        }

    }
}