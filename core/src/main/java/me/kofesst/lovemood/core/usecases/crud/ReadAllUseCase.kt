package me.kofesst.lovemood.core.usecases.crud

/**
 * Use case получения всех моделей.
 *
 * Возвращает список всех полученных моделей.
 */
@Deprecated("Deprecated")
fun interface ReadAllUseCase<Model : Any> {
    suspend operator fun invoke(): List<Model>
}