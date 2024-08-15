package me.kofesst.lovemood.core.usecases

import me.kofesst.lovemood.core.usecases.datastore.DataStoreUseCases
import me.kofesst.lovemood.core.usecases.models.PhotoMemoryUseCases
import me.kofesst.lovemood.core.usecases.models.ProfileUseCases
import me.kofesst.lovemood.core.usecases.models.RelationshipEventUseCases
import me.kofesst.lovemood.core.usecases.models.RelationshipUseCases

/**
 * Use cases всего приложения.
 */
data class AppUseCases(
    val profile: ProfileUseCases,
    val relationship: RelationshipUseCases,
    val relationshipEvents: RelationshipEventUseCases,
    val memories: PhotoMemoryUseCases,
    val dataStore: DataStoreUseCases
)