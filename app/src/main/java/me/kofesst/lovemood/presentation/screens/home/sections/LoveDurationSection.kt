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
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.delay
import me.kofesst.lovemood.R
import me.kofesst.lovemood.app.LocalDictionary
import me.kofesst.lovemood.app.dictionary
import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.core.ui.components.cards.BaseCard
import me.kofesst.lovemood.features.date.DatePeriod
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
            backgroundImagePainter = painterResource(R.drawable.ic_love_clock),
            label = dictionary.screens.home.loveDurationSectionLabel.string()
        ) {
            val dictionary = LocalDictionary.current
            Text(
                text = dictionary.dates.buildAnnotatedPeriodString(
                    period = lovePeriod,
                    ifPeriodEmpty = dictionary.screens.home.startDateWhenUnitsAreEmpty.string()
                )
            )
        }
    }
}