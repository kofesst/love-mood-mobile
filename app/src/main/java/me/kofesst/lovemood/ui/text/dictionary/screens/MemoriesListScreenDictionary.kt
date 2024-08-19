package me.kofesst.lovemood.ui.text.dictionary.screens

import android.content.Context
import me.kofesst.lovemood.R
import me.kofesst.lovemood.ui.text.ResourceText

class MemoriesListScreenDictionary(appContext: Context) {
    val screenTitle = ResourceText(
        R.string.screens__memories_list__screen_title, appContext
    )
    val memoriesCalendarCardLabel = ResourceText(
        R.string.screens__memories_list__memories_calendar_card_label, appContext
    )
    val memoriesCalendarCardText = ResourceText(
        R.string.screens__memories_list__memories_calendar_card_text, appContext
    )
    val openMemoriesCalendarAction = ResourceText(
        R.string.screens__memories_list__open_memories_calendar_action, appContext
    )
    val addNewMemoryAction = ResourceText(
        R.string.screens__memories_list__add_new_memory_action, appContext
    )
}