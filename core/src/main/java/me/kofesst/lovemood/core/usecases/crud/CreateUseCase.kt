package me.kofesst.lovemood.core.usecases.crud

/**
 * Use case создания модели.
 *
 * Возвращает ID новой модели.
 */
@Deprecated("Deprecated")
fun interface CreateUseCase<Model : Any> {
    suspend operator fun invoke(model: Model): Int
}