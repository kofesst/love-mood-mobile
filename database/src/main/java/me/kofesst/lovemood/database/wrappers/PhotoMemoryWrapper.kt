package me.kofesst.lovemood.database.wrappers

import me.kofesst.lovemood.core.models.PhotoMemory
import me.kofesst.lovemood.database.entities.PhotoMemoryEntity
import me.kofesst.lovemood.features.date.epochMillis
import me.kofesst.lovemood.features.date.localDate
import me.kofesst.lovemood.features.date.localDateTime

object PhotoMemoryWrapper : EntityWrapper<PhotoMemoryEntity, PhotoMemory> {
    override suspend fun PhotoMemory.asEntity(): PhotoMemoryEntity {
        return PhotoMemoryEntity(
            id = id,
            photoContent = photoContent,
            addedAt = addedAt.epochMillis,
            associatedDate = associatedDate?.epochMillis
        )
    }

    override suspend fun PhotoMemoryEntity.asModel(): PhotoMemory {
        return PhotoMemory(
            id = id,
            photoContent = photoContent,
            addedAt = addedAt.localDateTime,
            associatedDate = associatedDate?.localDate
        )
    }
}