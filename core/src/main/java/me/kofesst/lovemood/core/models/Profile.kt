package me.kofesst.lovemood.core.models

import java.time.LocalDate

/**
 * Модель профиля пользователя.
 *
 * [id] - уникальный ID профиля.
 *
 * [username] - имя профиля.
 *
 * [gender] - пол пользователя.
 *
 * [dateOfBirth] - дата рождения пользователя.
 *
 * [avatarContent] - контент изображения аватара профиля.
 */
@Suppress("ArrayInDataClass")
data class Profile(
    val id: Int,
    val username: String,
    val gender: Gender,
    val dateOfBirth: LocalDate,
    val avatarContent: ByteArray
)
