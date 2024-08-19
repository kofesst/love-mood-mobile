package me.kofesst.lovemood.presentation.forms.memory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.kofesst.lovemood.app.LocalAppState
import me.kofesst.lovemood.app.LocalDateTimePattern
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.core.ui.components.input.CheckField
import me.kofesst.lovemood.core.ui.components.input.LargeImagePickerField
import me.kofesst.lovemood.core.ui.components.input.TextInputField

data class MemoryFormDictionary(
    val photoPickerLabel: AppTextHolder,
    val photoPickerAction: AppTextHolder,
    val isAssociatedWithDateLabel: AppTextHolder,
    val associatedDateFieldLabel: AppTextHolder
)

fun LazyListScope.memoryFormContent(
    dictionary: MemoryFormDictionary,
    form: MemoryFormState,
    onFormAction: (MemoryFormAction) -> Unit
) {
    item(key = "photo_picker_field") {
        PhotoPickerField(
            modifier = Modifier.fillMaxWidth(),
            dictionary = dictionary,
            content = form.photoContent,
            onContentLoad = {
                onFormAction(
                    MemoryFormAction.PhotoContentChanged(
                        it
                    )
                )
            }
        )
    }
    item(key = "link_to_date_check_field") {
        LinkToDateCheckField(
            modifier = Modifier.fillMaxWidth(),
            dictionary = dictionary,
            value = form.isMemoryAssociatedWithDate,
            onValueChange = {
                onFormAction(
                    MemoryFormAction.IsAssociatedWithDateSwitched(it)
                )
            }
        )
    }
    item(key = "associated_date_field") {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = form.isMemoryAssociatedWithDate,
            enter = slideInVertically { -it },
            exit = slideOutVertically { -it }
        ) {
            AssociatedDateField(
                modifier = Modifier.fillMaxWidth(),
                dictionary = dictionary,
                value = form.associatedDate,
                onValueChange = {
                    onFormAction(
                        MemoryFormAction.AssociatedDateChanged(it)
                    )
                },
                error = form.associatedDateError?.string()
            )
        }
    }
}

@Composable
private fun PhotoPickerField(
    modifier: Modifier = Modifier,
    dictionary: MemoryFormDictionary,
    content: ByteArray,
    onContentLoad: (ByteArray) -> Unit
) {
    val appState = LocalAppState.current
    LargeImagePickerField(
        modifier = modifier,
        loadedContent = content,
        onContentLoad = onContentLoad,
        coroutineScope = appState.coroutineScope,
        label = dictionary.photoPickerLabel.string(),
        action = dictionary.photoPickerAction.string()
    )
}

@Composable
private fun LinkToDateCheckField(
    modifier: Modifier = Modifier,
    dictionary: MemoryFormDictionary,
    value: Boolean,
    onValueChange: (Boolean) -> Unit
) {
    CheckField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = dictionary.isAssociatedWithDateLabel.string()
    )
}

@Composable
private fun AssociatedDateField(
    modifier: Modifier = Modifier,
    dictionary: MemoryFormDictionary,
    value: String,
    onValueChange: (String) -> Unit,
    error: String?
) {
    val dateTimePattern = LocalDateTimePattern.current
    TextInputField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = dictionary.associatedDateFieldLabel.string(),
        placeholder = dateTimePattern.displayDatePattern,
        error = error,
        singleLine = true,
        maxLines = 1
    )
}