package me.kofesst.lovemood.core.ui.components.input

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import me.kofesst.lovemood.features.date.epochMillis
import me.kofesst.lovemood.features.date.localDate
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

/**
 * Поле выбора даты.
 *
 * [modifier] - модификатор компонента.
 *
 * [value] - текущее значение.
 *
 * [onValueChange] - коллбек при изменении значения.
 *
 * [colors] - цвета поля.
 *
 * [typography] - типографика поля.
 *
 * [contentPadding] - отступы поля.
 *
 * [containerShape] - фигура поля.
 *
 * [label] - наименование поля.
 *
 * [errorMessage] - текст ошибки.
 *
 * [enabled] - доступно ли поле для взаимодействия.
 *
 * [interactionSource] - источник взаимодействия.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    modifier: Modifier = Modifier,
    value: LocalDate,
    onValueChange: (LocalDate) -> Unit,
    colors: TextInputFieldColors = TextInputFieldDefaults.colors(),
    typography: TextInputFieldTypography = TextInputFieldDefaults.typography(),
    contentPadding: PaddingValues = InputFieldContainerDefaults.contentPadding,
    containerShape: Shape = InputFieldContainerDefaults.containerShape,
    label: String? = null,
    errorMessage: String? = null,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
) {
    val dateFormatter = remember { DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM) }
    val interactionSource = remember(interactionSource) {
        interactionSource ?: MutableInteractionSource()
    }
    TextInputField(
        modifier = modifier,
        colors = colors,
        typography = typography,
        contentPadding = contentPadding,
        containerShape = containerShape,
        label = label,
        errorMessage = errorMessage,
        interactionSource = interactionSource,
        value = dateFormatter.format(value),
        onValueChange = {},
        isReadOnly = true,
        maxLines = 1,
        singleLine = true
    )

    val pressedState = interactionSource.collectIsPressedAsState()
    val datePickerVisibleState = remember { mutableStateOf(enabled && pressedState.value) }
    LaunchedEffect(enabled, pressedState.value) {
        if (enabled && pressedState.value) {
            datePickerVisibleState.value = true
        }
    }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = value.epochMillis
    )
    LaunchedEffect(value) {
        datePickerState.selectedDateMillis = value.epochMillis
    }
    if (datePickerVisibleState.value) {
        DatePickerDialog(
            onDismissRequest = { datePickerVisibleState.value = false },
            confirmButton = {
                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        datePickerVisibleState.value = false
                        datePickerState.selectedDateMillis?.let {
                            onValueChange(it.localDate)
                        }
                    }
                ) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                showModeToggle = false
            )
        }
    }
}