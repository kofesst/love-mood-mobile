package me.kofesst.lovemood.features.date

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

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