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
import me.kofesst.lovemood.core.interactor.relationship.RelationshipInteractor
import me.kofesst.lovemood.features.date.DatePeriod
import me.kofesst.lovemood.localization.AppLocalization
import java.time.LocalDate

@HiltWorker
class RelationshipWidgetWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted parameters: WorkerParameters,
    private val relationshipInteractor: RelationshipInteractor,
    private val localization: AppLocalization
) : CoroutineWorker(context, parameters) {
    companion object {
        const val WORKER_TAG = "RelationshipWidgetWorker"

        @JvmStatic
        suspend fun updateWidgets(
            context: Context,
            relationshipInteractor: RelationshipInteractor,
            localization: AppLocalization
        ): Result {
            val glanceIds = GlanceAppWidgetManager(context).getGlanceIds(
                provider = RelationshipWidget::class.java
            )
            val widgetData = getWidgetData(
                relationshipInteractor = relationshipInteractor,
                localization = localization
            ) ?: return Result.failure()
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

        @JvmStatic
        suspend fun getWidgetData(
            relationshipInteractor: RelationshipInteractor,
            localization: AppLocalization
        ): RelationshipWidgetData? {
            val relationship = relationshipInteractor.get().getOrNull() ?: return null
            val loveDurationPeriod = DatePeriod(
                startDate = relationship.startDate,
                endDate = LocalDate.now()
            )
            val loveDuration = localization.datePeriod.apply {
                period = loveDurationPeriod
            }.build()
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

    override suspend fun doWork(): Result = updateWidgets(
        context = context,
        relationshipInteractor = relationshipInteractor,
        localization = localization
    )
}