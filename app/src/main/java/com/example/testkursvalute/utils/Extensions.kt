package com.example.testkursvalute.utils

import android.content.res.Resources
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.text.DecimalFormat

fun Double?.rubleString(): String {
    return this?.let {
        val numberFormat = DecimalFormat("#,##0.00")
        "${numberFormat.format(this)} ₽ "
    } ?: ""
}

fun String?.emptyIfNull(): String {
    return this ?: ""
}

fun String?.trimParanthesis(): String {
    return this?.replace(Regex("[()]"), "") ?: ""
}

fun setValuteSymbol(charCode: String): String {
    val symbolId = Resources.getSystem().getIdentifier(
        "s_${charCode?.lowercase()}",
        "strings",
        "com.example.testkursvalute.utils"
    )
    return if (symbolId == 0) "" else Resources.getSystem().getString(symbolId)
}

fun <T> LiveData<T>.doOnChange(owner: LifecycleOwner, f: (T) -> Unit) {
    observe(owner, Observer {
        f(it)
    })
}