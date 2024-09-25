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
) {
    override fun equals(other: Any?): Boolean {
        if (other == null) return false
        if (other !is Profile) return false

        if (id != other.id) return false
        if (username != other.username) return false
        if (dateOfBirth != other.dateOfBirth) return false
        if (!(avatarContent contentEquals other.avatarContent)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id
        result = 31 * result + username.hashCode()
        result = 31 * result + dateOfBirth.hashCode()
        result = 31 * result + avatarContent.contentHashCode()
        return result
    }
}