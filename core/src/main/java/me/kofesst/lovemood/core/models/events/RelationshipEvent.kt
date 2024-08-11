package me.kofesst.lovemood.core.models.events

import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.text.AppTextHolder
import java.time.Period

/**
 * Базовая модель события в отношениях.
 */
abstract class RelationshipEvent {
    /**
     * Уникальный ID события.
     */
    abstract val id: Int

    /**
     * Является ли событие стандартным.
     */
    abstract val isDefault: Boolean

    /**
     * Название события.
     */
    abstract val nameHolder: AppTextHolder

    /**
     * Модель отношений.
     */
    abstract val relationship: Relationship

    /**
     * Возвращает оставшееся время до события.
     */
    abstract fun getRemaining(): Period
}