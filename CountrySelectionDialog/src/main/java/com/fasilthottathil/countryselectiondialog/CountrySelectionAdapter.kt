package com.fasilthottathil.countryselectiondialog

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.fasilthottathil.countryselectiondialog.databinding.CountryItemBinding
import com.fasilthottathil.countryselectiondialog.models.CountryItem

class CountrySelectionAdapter : RecyclerView.Adapter<CountrySelectionAdapter.ViewHolder>(), Filterable {

    private var country: ArrayList<CountryItem> = arrayListOf()
    private var countryFiltered: ArrayList<CountryItem> = arrayListOf()
    private var onCountrySelectedListener: ((CountryItem) -> Unit)? = null

    class ViewHolder(val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CountryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {

            txtCountry.setText(
                HtmlCompat.fromHtml(
                    "<font color='#9DACFF'>${countryFiltered[position].dial_code}</font> ${countryFiltered[position].name}",
                    0
                ), TextView.BufferType.SPANNABLE
            )
            layout.setOnClickListener {
                onCountrySelectedListener?.let {
                    it(countryFiltered[position])
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return countryFiltered.size
    }

    override fun getItemViewType(position: Int): Int {
        return (position)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newList: ArrayList<CountryItem>) {
        country = newList
        countryFiltered = country
        notifyDataSetChanged()
    }

    fun setOnCountrySelectedListener(listener: (CountryItem) -> Unit) {
        onCountrySelectedListener = listener
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(char: CharSequence?): FilterResults {
                val charString = char?.toString() ?: ""
                countryFiltered = if (charString.isEmpty()) country else {
                    val filteredList = ArrayList<CountryItem>()
                    country
                        .filter {
                            (it.name.contains(char!!, true)) or (it.dial_code.contains(char, true))
                        }
                        .forEach { filteredList.add(it) }
                    filteredList
                }

                return FilterResults().apply {
                    values = countryFiltered
                }
            }

            @SuppressLint("NotifyDataSetChanged")
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                countryFiltered = if (results?.values == null)
                    arrayListOf()
                else
                    results.values as ArrayList<CountryItem>
                notifyDataSetChanged()
            }
        }
    }


}