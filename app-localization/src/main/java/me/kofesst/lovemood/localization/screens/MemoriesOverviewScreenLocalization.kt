package me.kofesst.lovemood.localization.screens

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import me.kofesst.lovemood.localization.R
import me.kofesst.lovemood.localization.text.TextBuilder
import me.kofesst.lovemood.localization.text.buildResource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MemoriesOverviewScreenLocalization @Inject constructor(
    @ApplicationContext context: Context
) {
    val addNewMemory: TextBuilder =
        context buildResource R.string.memories_overview__add_new_memory
}