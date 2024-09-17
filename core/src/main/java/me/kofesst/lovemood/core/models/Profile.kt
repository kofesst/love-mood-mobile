package me.kofesst.lovemood.core.models

import java.time.LocalDate

/**
 * Модель профиля пользователя.
 *
 * [id] - уникальный ID профиля.
 *
 * [username] - имя профиля.
 *
 * [dateOfBirth] - дата рождения пользователя.
 *
 * [avatarContent] - контент изображения аватара профиля.
 */
@Suppress("ArrayInDataClass")
data class Profile(
    val id: Int,
    val username: String,
    val dateOfBirth: LocalDate,
    val avatarContent: ByteArray
)
