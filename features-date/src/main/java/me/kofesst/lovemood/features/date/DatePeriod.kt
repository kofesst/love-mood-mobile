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
     * Нативный ненормализованный объект промежутка,
     * инициализованный из [startDate] и [endDate].
     */
    val javaPeriod: Period = Period.between(startDate, endDate)

    /**
     * Нативный нормализованный объект промежутка,
     * инициализованный из [startDate] и [endDate].
     */
    val normalizedJavaPeriod: Period = javaPeriod.normalized()

    /**
     * Полное количество лет в промежутке.
     */
    val fullYears: Int = javaPeriod.years

    /**
     * Нормализованное количество лет в промежутке.
     */
    val years: Int = javaPeriod.years

    /**
     * Полное количество месяцев в промежутке.
     */
    val fullMonths: Int = javaPeriod.months

    /**
     * Нормализованное количество месяцев в промежутке.
     */
    val months: Int = javaPeriod.months

    /**
     * Полное количество дней в промежутке.
     */
    val fullDays: Int = javaPeriod.days

    /**
     * Нормализованное количество дней в промежутке.
     */
    val days: Int = javaPeriod.days

    companion object {
        /**
         * Пустой объект промежутка.
         */
        val Empty = DatePeriod(LocalDate.MIN, LocalDate.MIN)
    }
}
