package me.kofesst.lovemood.localization.text

import me.kofesst.lovemood.features.date.DatePeriod

/**
 * Построитель текста периода дат.
 */
class DatePeriodTextBuilder : TextBuilder() {
    /**
     * Построитель текста в случае, если период пустой.
     */
    var emptyPeriodTextBuilder: TextBuilder = Empty

    /**
     * Склонения слова "Год".
     */
    var yearCasesBuilder: TextCasesBuilder? = null
    /**
     * Склонения слова "Месяц".
     */
    var monthCasesBuilder: TextCasesBuilder? = null
    /**
     * Склонения слова "День".
     */
    var dayCasesBuilder: TextCasesBuilder? = null
    /**
     * Период.
     */
    var period: DatePeriod? = null

    override fun prepareText(): Pair<String, List<Any>> {
        if (period == null) return "" to emptyList()

        val text = buildString {
            val units: Map<TextCasesBuilder, Int> = mapOf(
                yearCasesBuilder to period!!.years,
                monthCasesBuilder to period!!.months,
                dayCasesBuilder to period!!.days
            )
                .filterValues { it > 0 }
                .filterKeys { it != null }
                .mapKeys { (key, _) -> key!! }
            if (units.isEmpty()) {
                append(emptyPeriodTextBuilder.build())
            }
            units.keys.forEachIndexed { index, unitCasesBuilder ->
                val unit = units[unitCasesBuilder] ?: -1
                append(
                    unitCasesBuilder
                        .apply { amount = unit }
                        .build()
                )
                if (index != units.size - 1) append(" ")
            }
        }
        return text to emptyList()
    }
}