package me.kofesst.lovemood.core.repository

import me.kofesst.lovemood.core.models.Relationship

/**
 * Репозиторий данных отношений.
 */
interface RelationshipRepository {
    /**
     * Возвращает отношения по ID [relationshipId].
     *
     * Если отношения не найдены, возвращает null.
     */
    suspend fun getRelationship(relationshipId: Int): Relationship?

    /**
     * Создает отношения и возвращает ID созданной модели.
     *
     * [relationshipData] - данные отношений.
     */
    suspend fun createRelationship(relationshipData: Relationship): Int

    /**
     * Обновляет отношения.
     *
     * [relationshipData] - данные отношений.
     */
    suspend fun updateRelationship(relationshipData: Relationship)
}