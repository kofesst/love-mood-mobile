package me.kofesst.lovemood.core.text

interface TextWithPlaceholders : AppTextHolder {
    fun string(): String

    override fun string(vararg args: Any): String {
        val baseString = string()
        if (args.isEmpty()) return baseString

        val placeholdersValues = args.toList().mapNotNull { it as? Pair<*, *> }
        if (placeholdersValues.isEmpty()) return baseString

        return placeholdersValues.fold(initial = baseString) { acc, (pName, pValue) ->
            acc.replace(pName.toString(), pValue.toString())
        }
    }
}