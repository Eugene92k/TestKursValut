package com.example.testkursvalute.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testkursvalute.R
import com.example.testkursvalute.data.local.database.ValuteEntity
import com.example.testkursvalute.utils.PriceHelper
import com.example.testkursvalute.utils.emptyIfNull
import com.example.testkursvalute.utils.rubleString
import com.example.testkursvalute.utils.setValuteSymbol
import kotlinx.android.synthetic.main.item_valute_list.view.*

interface OnItemClickCallback {
    fun onFavouriteClick(id: String)
}

class ValuteAdapter(private val onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<ValuteAdapter.ValuteViewHolder>() {
    private val valutesList = mutableListOf<ValuteEntity>()

    class ValuteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(model: ValuteEntity, onItemClickCallback: OnItemClickCallback) {
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
                if (model.isFavourite) R.drawable.favourite_orange
                else R.drawable.favourite_white
            )

            itemView.valuteItemFavouriteImageView.setOnClickListener {
                onItemClickCallback.onFavouriteClick(model.id)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValuteViewHolder {
        return ValuteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_valute_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ValuteViewHolder, position: Int) {
        holder.bind(valutesList[position], onItemClickCallback)
    }

    override fun getItemCount(): Int {
        return valutesList.size
    }

    fun updateList(list: List<ValuteEntity>) {
        this.valutesList.clear()
        this.valutesList.addAll(list)
        notifyDataSetChanged()
    }
}