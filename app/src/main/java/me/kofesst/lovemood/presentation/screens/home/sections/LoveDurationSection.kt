package me.kofesst.lovemood.presentation.screens.home.sections

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import kotlinx.coroutines.delay
import me.kofesst.lovemood.R
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.core.ui.components.cards.BaseCard
import me.kofesst.lovemood.core.ui.components.cards.BaseCardDefaults
import me.kofesst.lovemood.features.date.DatePeriod
import me.kofesst.lovemood.presentation.app.LocalDictionary
import me.kofesst.lovemood.presentation.app.dictionary
import me.kofesst.lovemood.ui.text.dictionary.AppDictionary
import java.time.LocalDate
import kotlin.time.Duration.Companion.minutes

@Composable
fun LoveDurationSection(
    modifier: Modifier = Modifier,
    relationship: Relationship
) {
    var lovePeriod by remember {
        mutableStateOf(
            DatePeriod(
                startDate = relationship.startDate,
                endDate = LocalDate.now()
            )
        )
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(5.minutes) // recompose every 5 minutes
            lovePeriod = DatePeriod(
                startDate = relationship.startDate,
                endDate = LocalDate.now()
            )
        }
    }

    key(lovePeriod) {
        BaseCard(
            modifier = modifier,
            colors = BaseCardDefaults.colors(
                backgroundImageTint = Color(0xFFFECCC0)
            ),
            backgroundImagePainter = painterResource(R.drawable.ic_love_clock),
            label = dictionary.screens.home.loveDurationSectionLabel.string()
        ) {
            Text(
                text = buildAnnotatedString {
                    appendPeriod(
                        period = lovePeriod,
                        dictionary = LocalDictionary.current
                    )
                }
            )
        }
    }
}

private fun AnnotatedString.Builder.appendPeriod(
    period: DatePeriod,
    dictionary: AppDictionary,
    unitSpanStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.Bold),
    unitNameSpanStyle: SpanStyle = SpanStyle(),
    unitsDelimiter: String = " "
) {
    val units = mapOf(
        dictionary.dates.yearsTextCases to period.years,
        dictionary.dates.monthsTextCases to period.months,
        dictionary.dates.daysTextCases to period.days
    ).filter { it.value > 0 }
    if (units.isEmpty()) {
        append(dictionary.screens.home.startDateWhenUnitsAreEmpty.string())
    }
    units.keys.forEachIndexed { index, unitNameHolder ->
        val unit = units[unitNameHolder] ?: -1
        appendPeriodUnit(unit, unitNameHolder, unitSpanStyle, unitNameSpanStyle)
        if (index != units.size - 1) append(unitsDelimiter)
    }
}

private fun AnnotatedString.Builder.appendPeriodUnit(
    unit: Int,
    unitNameHolder: AppTextHolder,
    unitSpanStyle: SpanStyle = SpanStyle(fontWeight = FontWeight.Bold),
    unitNameSpanStyle: SpanStyle = SpanStyle()
) {
    withStyle(unitSpanStyle) {
        append(unit.toString())
    }
    append(" ")
    withStyle(unitNameSpanStyle) {
        append(unitNameHolder.string(unit, false))
    }
}