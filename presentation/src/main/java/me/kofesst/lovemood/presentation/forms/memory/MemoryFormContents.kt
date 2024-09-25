package me.kofesst.lovemood.presentation.forms.memory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import me.kofesst.lovemood.core.ui.components.input.CheckField
import me.kofesst.lovemood.core.ui.components.input.DatePickerField
import me.kofesst.lovemood.core.ui.components.input.ImagePicker
import me.kofesst.lovemood.localization.screens.MemoryFormLocalization
import me.kofesst.lovemood.presentation.localization
import java.time.LocalDate

object MemoryFormContents {
    @Composable
    fun Content(
        modifier: Modifier,
        form: MemoryFormState,
        onFormAction: (MemoryFormAction) -> Unit
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(space = 40.dp)
        ) {
            val localization = localization.screens.memoryForm
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(space = 10.dp)
            ) {
                Text(
                    text = localization.formTitle.build(),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = localization.formSubtitle.build(),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            PhotoPickerField(
                modifier = Modifier.fillMaxWidth(),
                localization = localization,
                content = form.photoContent,
                onContentLoad = {
                    onFormAction(
                        MemoryFormAction.PhotoContentChanged(it)
                    )
                }
            )
            AssociateWithDateCheckField(
                modifier = Modifier.fillMaxWidth(),
                localization = localization,
                value = form.isMemoryAssociatedWithDate,
                onValueChange = {
                    onFormAction(
                        MemoryFormAction.IsAssociatedWithDateSwitched(it)
                    )
                }
            )
            AnimatedVisibility(
                modifier = Modifier.fillMaxWidth(),
                visible = form.isMemoryAssociatedWithDate,
                enter = fadeIn() + slideInVertically { -it },
                exit = fadeOut() + slideOutVertically { -it }
            ) {
                AssociatedDateField(
                    modifier = Modifier.fillMaxWidth(),
                    localization = localization,
                    value = form.associatedDate,
                    onValueChange = {
                        onFormAction(
                            MemoryFormAction.AssociatedDateChanged(it)
                        )
                    },
                    error = form.associatedDateError?.build()
                )
            }
        }
    }

    @Composable
    private fun PhotoPickerField(
        modifier: Modifier = Modifier,
        localization: MemoryFormLocalization,
        content: ByteArray,
        onContentLoad: (ByteArray) -> Unit
    ) {
        ImagePicker(
            modifier = modifier,
            imageContent = content,
            onContentChange = onContentLoad,
            selectImageAction = {
                Text(text = localization.selectPhotoLabel.build())
            },
            removeImageAction = {
                Text(text = localization.removePhotoLabel.build())
            }
        )
    }

    @Composable
    private fun AssociateWithDateCheckField(
        modifier: Modifier = Modifier,
        localization: MemoryFormLocalization,
        value: Boolean,
        onValueChange: (Boolean) -> Unit
    ) {
        CheckField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            label = localization.associateWithDateLabel.build()
        )
    }

    @Composable
    private fun AssociatedDateField(
        modifier: Modifier = Modifier,
        localization: MemoryFormLocalization,
        value: LocalDate,
        onValueChange: (LocalDate) -> Unit,
        error: String?
    ) {
        DatePickerField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            label = localization.associatedDateLabel.build(),
            errorMessage = error
        )
    }
}