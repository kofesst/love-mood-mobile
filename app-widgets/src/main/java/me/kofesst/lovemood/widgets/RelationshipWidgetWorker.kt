package me.kofesst.lovemood.widgets

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import me.kofesst.lovemood.core.usecases.AppUseCases
import me.kofesst.lovemood.features.date.DatePeriod
import me.kofesst.lovemood.localization.dictionary.AppDictionary
import java.time.LocalDate

@HiltWorker
class RelationshipWidgetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted parameters: WorkerParameters,
    private val useCases: AppUseCases,
    private val dictionary: AppDictionary
) : CoroutineWorker(context, parameters) {
    companion object {
        const val WORKER_TAG = "RelationshipWidgetWorker"

        @JvmStatic
        suspend fun updateWidgets(
            context: Context,
            useCases: AppUseCases,
            dictionary: AppDictionary
        ): Result {
            val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(
                provider = RelationshipWidget::class.java
            )
            val widgetData = getWidgetData(useCases, dictionary) ?: return Result.failure()
            glanceIds.forEach { glanceId ->
                updateWidget(context, glanceId, widgetData)
            }
            return Result.success()
        }

        @JvmStatic
        suspend fun updateWidget(
            context: Context,
            glanceWidgetId: GlanceId,
            widgetData: RelationshipWidgetData
        ) {
            updateAppWidgetState(context, glanceWidgetId) { preferences ->
                preferences[RelationshipWidget.relationshipIdKey] = widgetData.relationshipId
                preferences[RelationshipWidget.userUsernameKey] = widgetData.userUsername
                preferences[RelationshipWidget.userAvatarEncodedKey] = RelationshipWidget
                    .encodeAvatar(widgetData.userAvatar)
                preferences[RelationshipWidget.partnerUsernameKey] = widgetData.partnerUsername
                preferences[RelationshipWidget.partnerAvatarEncodedKey] = RelationshipWidget
                    .encodeAvatar(widgetData.partnerAvatar)
                preferences[RelationshipWidget.loveDurationKey] = widgetData.loveDuration
            }
            RelationshipWidget.update(context, glanceWidgetId)
        }

        suspend fun getWidgetData(
            useCases: AppUseCases,
            dictionary: AppDictionary
        ): RelationshipWidgetData? {
            val userProfileId = useCases.dataStore.getSettings().userProfileId
                ?: return null
            val relationship = useCases.relationship.readByProfileId(userProfileId)
                ?: return null
            val loveDurationPeriod = DatePeriod(
                startDate = relationship.startDate,
                endDate = LocalDate.now()
            )
            val loveDuration = dictionary.dates.buildAnnotatedPeriodString(
                period = loveDurationPeriod
            ).toString()
            val widgetData = RelationshipWidgetData(
                relationshipId = relationship.id,
                userUsername = relationship.userProfile.username,
                userAvatar = relationship.userProfile.avatarContent,
                partnerUsername = relationship.partnerProfile.username,
                partnerAvatar = relationship.partnerProfile.avatarContent,
                loveDuration = loveDuration
            )
            return widgetData
        }
    }

    override suspend fun doWork(): Result = updateWidgets(context, useCases, dictionary)
}