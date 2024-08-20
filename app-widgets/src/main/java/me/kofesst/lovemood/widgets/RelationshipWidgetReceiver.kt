package me.kofesst.lovemood.widgets

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import me.kofesst.lovemood.core.usecases.AppUseCases
import me.kofesst.lovemood.localization.dictionary.AppDictionary
import javax.inject.Inject
import kotlin.coroutines.EmptyCoroutineContext

@AndroidEntryPoint
class RelationshipWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = RelationshipWidget

    @Inject
    lateinit var useCases: AppUseCases

    @Inject
    lateinit var dictionary: AppDictionary

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.d("LoveMoodWidget", "Received intent: ${intent.action}")
        CoroutineScope(EmptyCoroutineContext).launch {
            when (intent.action) {
                AppWidgetManager.ACTION_APPWIDGET_ENABLED,
                AppWidgetManager.ACTION_APPWIDGET_UPDATE,
                AppWidgetManager.ACTION_APPWIDGET_OPTIONS_CHANGED -> updateWidget(context)
            }
        }
    }

    private suspend fun updateWidget(context: Context) {
        Log.d("LoveMoodWidget", "Updating all widgets")
        RelationshipWidgetWorker.updateWidgets(context, useCases, dictionary)
    }
}