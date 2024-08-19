package me.kofesst.lovemood.ui.text.dictionary.screens

import android.content.Context
import me.kofesst.lovemood.R
import me.kofesst.lovemood.ui.text.ResourceText

class HomeScreenDictionary(appContext: Context) {
    val loveDurationSectionLabel = ResourceText(
        R.string.screens__home__love_duration_section_label, appContext
    )
    val eventsSectionLabel = ResourceText(
        R.string.screens__home__events_section_label, appContext
    )
    val memoriesSectionLabel = ResourceText(
        R.string.screens__home__memories_section_label, appContext
    )
    val doNotHaveMemories = ResourceText(
        R.string.screens__home__do_not_have_memories, appContext
    )
    val addFirstMemoryAction = ResourceText(
        R.string.screens__home__add_first_memory_action, appContext
    )
    val memoriesContentText = ResourceText(
        R.string.screens__home__memories_content_text, appContext
    )
    val viewAllMemoriesAction = ResourceText(
        R.string.screens__home__view_all_memories_action, appContext
    )
    val startDateWhenUnitsAreEmpty = ResourceText(
        R.string.screens__home__start_date_when_unit_are_empty, appContext
    )

    val userAndPartner = ResourceText(
        R.string.screens__home__user_and_partner, appContext
    )
    val editProfileAction = ResourceText(
        R.string.screens__home__edit_profile_action, appContext
    )
    val editRelationshipAction = ResourceText(
        R.string.screens__home__edit_relationship_action, appContext
    )
    val noRelationshipTitle = ResourceText(
        R.string.screens__home__no_relationship_title, appContext
    )
    val addRelationshipAction = ResourceText(
        R.string.screens__home__add_relationship_action, appContext
    )
}