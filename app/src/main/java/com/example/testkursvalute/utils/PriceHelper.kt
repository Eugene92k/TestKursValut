package com.example.testkursvalute.utils

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.testkursvalute.R

object PriceHelper {

    fun showChangePrice(textView: TextView, value: Double, previous: Double) {
        val change = value - previous
        val changePrice = "%.2f".format(change).trimParanthesis()
        textView.text = changePrice

        val context = textView.context

        if (changePrice.contains("-")) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.red))
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                ContextCompat.getDrawable(context, R.drawable.arrow_down), null)
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.green))
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null,
                ContextCompat.getDrawable(context, R.drawable.arrow_up), null)
        }
    }
}