package me.kofesst.lovemood.core.usecases.models

import me.kofesst.lovemood.core.models.events.RelationshipEvent
import me.kofesst.lovemood.core.usecases.crud.CrudUseCases

/**
 * CRUD use cases модели событий в отношениях.
 */
@Deprecated("Deprecated")
interface RelationshipEventUseCases : CrudUseCases<RelationshipEvent>