package me.kofesst.lovemood.core.models

/**
 * Класс, представляющий собой сохранённые на устройстве
 * настройки пользователя.
 *
 * [userProfileId] - ID профиля пользователя.
 * Если профиль ещё не создан, значение равно null.
 */
@Deprecated(
    message = "Deprecated",
    replaceWith = ReplaceWith("UserSession")
)
data class UserSettings(
    val userProfileId: Int?
)