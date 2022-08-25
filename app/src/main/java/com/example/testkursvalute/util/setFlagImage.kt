package com.example.testkursvalute.util

import android.content.res.Resources
import com.example.testkursvalute.R

fun setFlagImage(charCode: String): Int {
    var resString: Int = Resources.getSystem()
        .getIdentifier(
            "R.drawable.flg_${charCode?.lowercase()}",
            "drawable",
            "com.example.testkursvalute.util"
        )
    if (resString == 0) resString = R.drawable.baseline_image
    return resString
}