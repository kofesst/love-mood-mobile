package me.kofesst.lovemood.presentation.screens.home.sections

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import me.kofesst.lovemood.localization.text.TextBuilder
import kotlin.math.roundToLong

object LoveDurationFacts {
    object Heartbeat : LoveDurationFact() {
        const val DEFAULT_HEARTBEAT_PER_MINUTE: Int = 80

        override val fullText: TextBuilder = TextBuilder.fromString(
            "за это время Ваше сердце ударилось примерно"
        )

        override fun applyFormula(durationMillis: Long): Long {
            val heartbeatPerMilli = DEFAULT_HEARTBEAT_PER_MINUTE / 60f / 1000f
            return (heartbeatPerMilli * durationMillis).roundToLong()
        }

        override fun buildFactText(value: Long): String {
            return ""
        }
    }
}

abstract class LoveDurationFact {
    abstract val fullText: TextBuilder

    protected abstract fun applyFormula(durationMillis: Long): Long

    protected abstract fun buildFactText(value: Long): String

    fun buildText(durationMillis: Long): String {
        val value = applyFormula(durationMillis)
        val factText = buildFactText(value)
        return buildString {
            append(fullText.build())
            append(" ")
            append(factText)
        }
    }

    fun buildAnnotatedText(
        durationMillis: Long,
        defaultStyle: SpanStyle,
        factStyle: SpanStyle
    ): AnnotatedString {
        val value = applyFormula(durationMillis)
        val factText = buildFactText(value)
        return buildAnnotatedString {
            withStyle(defaultStyle) {
                append(fullText.build())
                append(" ")
            }
            withStyle(factStyle) {
                append(factText)
            }
        }
    }
}