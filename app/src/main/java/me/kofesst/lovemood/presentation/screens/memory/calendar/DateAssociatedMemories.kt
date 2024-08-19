package me.kofesst.lovemood.presentation.screens.memory.calendar

import me.kofesst.lovemood.core.models.PhotoMemory
import java.time.LocalDate

data class DateAssociatedMemories(
    val date: LocalDate,
    val memories: List<PhotoMemory>
)

fun List<PhotoMemory>.hasAssociated(): Boolean {
    return any { it.associatedDate != null }
}

fun List<PhotoMemory>.associated(): List<DateAssociatedMemories> {
    return this
        .filter { it.associatedDate != null }
        .groupBy { it.associatedDate!! }
        .map { (date, memories) -> DateAssociatedMemories(date, memories) }
}

fun List<DateAssociatedMemories>.byDate(date: LocalDate): List<PhotoMemory> {
    return firstOrNull { it.date == date }?.memories ?: emptyList()
}