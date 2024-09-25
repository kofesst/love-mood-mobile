package me.kofesst.lovemood.localization.screens

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScreensLocalization @Inject constructor(
    val home: HomeScreenLocalization,
    val userProfileForm: UserProfileFormLocalization,
    val partnerProfileForm: PartnerProfileFormLocalization,
    val relationshipForm: RelationshipFormLocalization,
    val memoriesOverview: MemoriesOverviewScreenLocalization,
    val memoryForm: MemoryFormLocalization
)