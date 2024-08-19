package me.kofesst.lovemood.core.models

import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Модель совместного момента.
 *
 * [id] - уникальный ID модели.
 *
 * [photoContent] - набор байтов изображения.
 *
 * [addedAt] - дата и время добавления момента.
 *
 * [associatedDate] - связанная с моментом дата.
 */
@Suppress("ArrayInDataClass")
data class PhotoMemory(
    val id: Int,
    val photoContent: ByteArray,
    val addedAt: LocalDateTime,
    val associatedDate: LocalDate?
)
