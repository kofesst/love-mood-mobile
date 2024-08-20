package me.kofesst.lovemood.localization

import android.content.Context
import androidx.annotation.StringRes
import me.kofesst.lovemood.core.text.TextWithPlaceholders

class ResourceText(
    @StringRes private val resId: Int,
    private val context: Context
) : TextWithPlaceholders {
    override fun string(): String {
        return context.getString(resId)
    }
}