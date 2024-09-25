package me.kofesst.lovemood.presentation.forms.relationship

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.kofesst.lovemood.core.ui.components.input.DatePickerField
import me.kofesst.lovemood.localization.screens.RelationshipFormLocalization
import java.time.LocalDate

object RelationshipFormContents {
    @Composable
    fun LoveStartDateContent(
        modifier: Modifier,
        localization: RelationshipFormLocalization,
        form: RelationshipFormState,
        onFormAction: (RelationshipFormAction) -> Unit
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(space = 40.dp)
        ) {
            Text(
                text = localization.loveStartDateStageTitle.build(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            StartDateField(
                modifier = Modifier.fillMaxWidth(),
                localization = localization,
                value = form.startDate,
                onValueChange = {
                    onFormAction(
                        RelationshipFormAction.StartDateChanged(it)
                    )
                },
                error = form.startDateError?.build()
            )
        }
    }

    @Composable
    private fun StartDateField(
        modifier: Modifier = Modifier,
        localization: RelationshipFormLocalization,
        value: LocalDate,
        onValueChange: (LocalDate) -> Unit,
        error: String?
    ) {
        DatePickerField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            label = localization.startDateLabel.build(),
            errorMessage = error
        )
    }
}