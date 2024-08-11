package me.kofesst.lovemood.core.usecases.models.relationship

import me.kofesst.lovemood.core.models.Relationship

/**
 * Use case для получения модели отношений по ID профиля
 * одного из партнёров.
 */
fun interface ReadRelationshipByProfileIdUseCase {
    suspend operator fun invoke(profileId: Int): Relationship?
}