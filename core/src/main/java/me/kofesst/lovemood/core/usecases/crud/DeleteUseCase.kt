package me.kofesst.lovemood.core.usecases.crud

/**
 * Use case удаления модели.
 */
fun interface DeleteUseCase<Model : Any> {
    suspend operator fun invoke(model: Model)
}