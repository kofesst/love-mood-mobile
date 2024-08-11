package me.kofesst.lovemood.core.usecases.models

import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.usecases.crud.CrudUseCases
import me.kofesst.lovemood.core.usecases.models.relationship.ReadRelationshipByProfileIdUseCase

/**
 * CRUD use cases модели отношений.
 */
interface RelationshipUseCases : CrudUseCases<Relationship> {
    val readByProfileId: ReadRelationshipByProfileIdUseCase
}