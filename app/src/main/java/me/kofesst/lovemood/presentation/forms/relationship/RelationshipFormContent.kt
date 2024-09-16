package me.kofesst.lovemood.presentation.forms.relationship

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.kofesst.lovemood.app.LocalDateTimePattern
import me.kofesst.lovemood.core.ui.components.input.TextInputField
import me.kofesst.lovemood.localization.dictionary.screens.forms.RelationshipFormDictionary

fun LazyListScope.relationshipFormContent(
    dictionary: RelationshipFormDictionary,
    form: RelationshipFormState,
    onFormAction: (RelationshipFormAction) -> Unit
) {
    item(key = "relationship_start_date_field") {
        StartDateField(
            modifier = Modifier.fillMaxWidth(),
            dictionary = dictionary,
            value = form.startDate,
            onValueChange = {
                onFormAction(
                    RelationshipFormAction.StartDateChanged(it)
                )
            },
            error = form.startDateError?.string()
        )
    }
}

@Composable
private fun StartDateField(
    modifier: Modifier = Modifier,
    dictionary: RelationshipFormDictionary,
    value: String,
    onValueChange: (String) -> Unit,
    error: String?
) {
    val dateTimePattern = LocalDateTimePattern.current
    TextInputField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = dictionary.startDateFieldLabel.string(),
        placeholder = dateTimePattern.displayDatePattern,
        errorMessage = error,
        singleLine = true,
        maxLines = 1
    )
}