package me.kofesst.lovemood.core.usecases.profile

import me.kofesst.lovemood.core.models.events.RelationshipEvent
import me.kofesst.lovemood.core.usecases.crud.CrudUseCases

/**
 * CRUD use cases модели событий в отношениях.
 */
interface RelationshipEventUseCases : CrudUseCases<RelationshipEvent>