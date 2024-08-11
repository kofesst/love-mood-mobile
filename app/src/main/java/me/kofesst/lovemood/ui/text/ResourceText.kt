package me.kofesst.lovemood.ui.text

import android.content.Context
import androidx.annotation.StringRes
import me.kofesst.lovemood.core.text.AppTextHolder

class ResourceText(
    @StringRes private val resId: Int,
    private val context: Context
) : AppTextHolder {
    override fun string(vararg args: Any): String {
        return context.getString(resId)
    }
}