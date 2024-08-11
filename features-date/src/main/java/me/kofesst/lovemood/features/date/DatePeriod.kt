package me.kofesst.lovemood.features.date

import java.time.LocalDate
import java.time.Period

/**
 * Представление промежутка времени между
 * двумя датами.
 *
 * [startDate] - начальная дата.
 *
 * [endDate] - конечная дата.
 */
data class DatePeriod constructor(
    val startDate: LocalDate,
    val endDate: LocalDate
) {
    init {
        require(startDate <= endDate) { "Start date is earlier than end date" }
    }

    /**
     * Нативный нормализованный объект промежутка,
     * инициализированный из [startDate] и [endDate].
     */
    val javaPeriod: Period = Period
        .between(startDate, endDate)
        .normalized()

    /**
     * Нормализованное количество лет в промежутке.
     */
    val years: Int = javaPeriod.years

    /**
     * Нормализованное количество месяцев в промежутке.
     */
    val months: Int = javaPeriod.months

    /**
     * Нормализованное количество дней в промежутке.
     */
    val days: Int = javaPeriod.days
}
