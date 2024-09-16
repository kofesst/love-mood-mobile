package me.kofesst.lovemood.database.wrappers

import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.database.entities.PhotoMemoryEntity
import me.kofesst.lovemood.features.date.epochMillis
import me.kofesst.lovemood.features.date.localDate
import me.kofesst.lovemood.features.date.localDateTime

/**
 * Преобразователь моделей и сущностей воспоминаний.
 */
internal object PhotoMemoryWrapper {
    fun PhotoMemory.asEntity(): PhotoMemoryEntity {
        return PhotoMemoryEntity(
            id = id,
            photoContent = photoContent,
            addedAt = addedAt.epochMillis,
            associatedDate = associatedDate?.epochMillis
        )
    }

    fun PhotoMemoryEntity.asModel(): PhotoMemory {
        return PhotoMemory(
            id = id,
            photoContent = photoContent,
            addedAt = addedAt.localDateTime,
            associatedDate = associatedDate?.localDate
        )
    }
}