package me.kofesst.lovemood.core.usecases.crud

/**
 * Use case получения модели по её ID.
 *
 * Возвращает полученную модель или null, если такой ID не найден.
 */
@Deprecated("Deprecated")
fun interface ReadByIdUseCase<Model : Any> {
    suspend operator fun invoke(modelId: Int): Model?
}