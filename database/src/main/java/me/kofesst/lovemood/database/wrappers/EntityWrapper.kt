package me.kofesst.lovemood.database.wrappers

@Deprecated(message = "Deprecated")
interface EntityWrapper<Entity : Any, Model : Any> {
    suspend fun Model.asEntity(): Entity
    suspend fun Entity.asModel(): Model
}