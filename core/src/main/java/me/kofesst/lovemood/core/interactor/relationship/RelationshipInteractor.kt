package me.kofesst.lovemood.core.interactor.relationship

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Интерактор отношений.
 */
@Singleton
class RelationshipInteractor @Inject constructor(
    val get: GetUserRelationship,
    val create: CreateRelationship,
    val update: UpdateRelationship
)