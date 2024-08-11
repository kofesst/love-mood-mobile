package me.kofesst.lovemood.database.impl.usecases

import me.kofesst.lovemood.core.usecases.crud.CreateUseCase
import me.kofesst.lovemood.core.usecases.crud.CrudUseCases
import me.kofesst.lovemood.core.usecases.crud.DeleteUseCase
import me.kofesst.lovemood.core.usecases.crud.UpdateUseCase
import me.kofesst.lovemood.database.dao.BaseDao
import me.kofesst.lovemood.database.wrappers.EntityWrapper

abstract class BaseCrudUseCaseImpl<Entity : Any, EntityDao : BaseDao<Entity>, Model : Any>(
    val wrapper: EntityWrapper<Entity, Model>,
    private val dao: EntityDao
) : CrudUseCases<Model> {
    override val create: CreateUseCase<Model> = CreateUseCase { model ->
        val entity = with(wrapper) { model.asEntity() }
        val newId = dao.insert(entity)
        newId.toInt()
    }

    override val update: UpdateUseCase<Model> = UpdateUseCase { model ->
        val entity = with(wrapper) { model.asEntity() }
        dao.update(entity)
    }

    override val deleteById: DeleteUseCase<Model> = DeleteUseCase { model ->
        val entity = with(wrapper) { model.asEntity() }
        dao.delete(entity)
    }
}