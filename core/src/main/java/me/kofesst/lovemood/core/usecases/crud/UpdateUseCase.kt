package me.kofesst.lovemood.core.usecases.crud

/**
 * Use case обновления модели.
 */
@Deprecated("Deprecated")
fun interface UpdateUseCase<Model : Any> {
    suspend operator fun invoke(model: Model)
}