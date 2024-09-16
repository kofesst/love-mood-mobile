package me.kofesst.lovemood.core.usecases.crud

/**
 * Use cases базовых операций над моделями.
 */
@Deprecated("Deprecated")
interface CrudUseCases<Model : Any> {
    val readAll: ReadAllUseCase<Model>
    val readById: ReadByIdUseCase<Model>
    val create: CreateUseCase<Model>
    val update: UpdateUseCase<Model>
    val deleteById: DeleteUseCase<Model>
}