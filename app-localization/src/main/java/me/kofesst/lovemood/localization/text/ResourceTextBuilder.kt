package me.kofesst.lovemood.localization.text

import android.content.Context
import androidx.annotation.StringRes

/**
 * Построитель текста из ресурсов приложения.
 *
 * [context] - контекст приложения.
 *
 * [resId] - ID ресурса.
 */
class ResourceTextBuilder(
    private val context: Context,
    @StringRes private val resId: Int
) : TextBuilder() {
    override fun prepareText(): Pair<String, List<Any>> {
        val text = context.getString(resId)
        return text to emptyList()
    }
}

internal infix fun Context.buildResource(@StringRes resId: Int): ResourceTextBuilder {
    return ResourceTextBuilder(this, resId)
}