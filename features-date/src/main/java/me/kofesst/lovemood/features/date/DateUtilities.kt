package me.kofesst.lovemood.features.date

import android.content.Context
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Представление форматирования даты и времени.
 *
 * [datePattern] - программный формат даты (без времени).
 *
 * [displayDatePattern] - отображаемый в приложении формат даты (без времени).
 */
data class DateTimePattern internal constructor(
    val datePattern: String,
    val displayDatePattern: String
) {
    private val dateFormatter = DateTimeFormatter.ofPattern(datePattern)

    companion object {
        /**
         * Создаёт объект представления форматирования из
         * текущего контекста приложения [context].
         */
        fun ofContext(context: Context): DateTimePattern {
            val resources = context.resources
            return DateTimePattern(
                datePattern = resources.getString(R.string.date_format),
                displayDatePattern = resources.getString(R.string.display_date_format)
            )
        }
    }

    /**
     * Получает дату (без времени) из текста [text].
     */
    fun parseDate(text: String): LocalDate {
        return LocalDate.parse(text, dateFormatter)
    }

    /**
     * Форматирует дату (без времени) [date].
     */
    fun formatDate(date: LocalDate): String {
        return dateFormatter.format(date)
    }
}

/**
 * Создаёт дату (с временем) из текущего количества миллисекунд.
 */
val Long.localDateTime: LocalDateTime
    get() = Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()

/**
 * Создаёт дату (без времени) из текущего количества миллисекунд.
 */
val Long.localDate: LocalDate
    get() = Instant
        .ofEpochMilli(this)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

/**
 * Миллисекунды даты и времени.
 */
val LocalDateTime.epochMillis: Long
    get() = this
        .atZone(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()

/**
 * Миллисекунды даты.
 */
val LocalDate.epochMillis: Long
    get() = this
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli()