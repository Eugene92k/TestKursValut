package com.example.testkursvalute.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testkursvalute.R
import com.example.testkursvalute.data.local.database.ValutesListEntity
import com.example.testkursvalute.util.PriceHelper
import com.example.testkursvalute.util.emptyIfNull
import com.example.testkursvalute.util.rubleString
import com.example.testkursvalute.util.setValuteSymbol

import kotlinx.android.synthetic.main.item_valute_list.view.*

interface OnItemClickCallback {

    fun onFavouriteClick(id: String)
}


class ValutesListAdapter(private val onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<ValutesListAdapter.ValutesListViewHolder>() {

    private val valutesList = mutableListOf<ValutesListEntity>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValutesListViewHolder {
        return ValutesListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_valute_list, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ValutesListViewHolder, position: Int) {
        holder.bind(valutesList[position], onItemClickCallback)
    }


    override fun getItemCount(): Int {
        return valutesList.size
    }


    fun updateList(list: List<ValutesListEntity>) {
        this.valutesList.clear()
        this.valutesList.addAll(list)
        notifyDataSetChanged()
    }

    class ValutesListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(model: ValutesListEntity, onItemClickCallback: OnItemClickCallback) {
            val charCode = model.charCode.emptyIfNull()

            itemView.valuteItemCharCodeTextView.text = charCode

            itemView.valuteItemNameTextView.text = model.name.emptyIfNull()
            itemView.valuteItemPriceTextView.text = model.value.rubleString()


            PriceHelper.showChangePrice(
                itemView.valuteItemChangeTextView,
                model.value ?: 0.00,
                model.previous ?: 0.00
            )

            itemView.valuteItemNominalTextView.text =
                "${model.nominal.toString().emptyIfNull()}${setValuteSymbol(charCode)}"



            itemView.valuteItemFavouriteImageView.setImageResource(
                if (model.isFavorite) R.drawable.favorite_orange
                else R.drawable.favorite_white
            )

            itemView.valuteItemFavouriteImageView.setOnClickListener {
                onItemClickCallback.onFavouriteClick(model.id)
            }
        }
    }
}

