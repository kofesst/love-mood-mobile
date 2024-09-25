package me.kofesst.lovemood.presentation.forms.profile

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
import me.kofesst.lovemood.core.ui.components.input.ImagePicker
import me.kofesst.lovemood.core.ui.components.input.TextInputField
import me.kofesst.lovemood.localization.screens.ProfileFormLocalization
import java.time.LocalDate

object ProfileFormContents {
    @Composable
    fun SelectingPictureStageContent(
        modifier: Modifier,
        localization: ProfileFormLocalization,
        form: ProfileFormState,
        onFormAction: (ProfileFormAction) -> Unit
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(space = 40.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(space = 10.dp)
            ) {
                Text(
                    text = localization.selectingPictureStageTitle.build(),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = localization.selectingPictureStageSubtitle.build(),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = localization.selectingPictureStageLabel.build(),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            AvatarPickerField(
                modifier = Modifier.fillMaxWidth(),
                localization = localization,
                content = form.avatarContent
            ) {
                onFormAction(
                    ProfileFormAction.AvatarContentChanged(it)
                )
            }
        }
    }

    @Composable
    fun EnteringDataStageContent(
        modifier: Modifier = Modifier,
        localization: ProfileFormLocalization,
        form: ProfileFormState,
        onFormAction: (ProfileFormAction) -> Unit
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(space = 40.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(space = 10.dp)
            ) {
                Text(
                    text = localization.enteringDataStageTitle.build(),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = localization.enteringDataStageSubtitle.build(),
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = localization.enteringDataStageLabel.build(),
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(space = 20.dp)
            ) {
                UsernameInputField(
                    modifier = Modifier.fillMaxWidth(),
                    localization = localization,
                    value = form.username,
                    onValueChange = {
                        onFormAction(
                            ProfileFormAction.UsernameChanged(it)
                        )
                    },
                    error = form.usernameError?.build()
                )
                DateOfBirthInputField(
                    modifier = Modifier.fillMaxWidth(),
                    localization = localization,
                    value = form.dateOfBirth,
                    onValueChange = {
                        onFormAction(
                            ProfileFormAction.DateOfBirthChanged(it)
                        )
                    },
                    error = form.dateOfBirthError?.build()
                )
            }
        }
    }


    @Composable
    private fun AvatarPickerField(
        modifier: Modifier = Modifier,
        localization: ProfileFormLocalization,
        content: ByteArray,
        onContentLoad: (ByteArray) -> Unit
    ) {
        ImagePicker(
            modifier = modifier,
            imageContent = content,
            onContentChange = onContentLoad,
            selectImageAction = {
                Text(text = localization.selectAvatarLabel.build())
            },
            removeImageAction = {
                Text(text = localization.removeAvatarLabel.build())
            }
        )
    }

    @Composable
    private fun UsernameInputField(
        modifier: Modifier = Modifier,
        localization: ProfileFormLocalization,
        value: String,
        onValueChange: (String) -> Unit,
        error: String?
    ) {
        TextInputField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            label = localization.usernameLabel.build(),
            placeholder = localization.usernamePlaceholder.build(),
            maxSymbols = 36,
            errorMessage = error,
            singleLine = true,
            maxLines = 1
        )
    }

    @Composable
    private fun DateOfBirthInputField(
        modifier: Modifier = Modifier,
        localization: ProfileFormLocalization,
        value: LocalDate,
        onValueChange: (LocalDate) -> Unit,
        error: String?
    ) {
        DatePickerField(
            modifier = modifier,
            value = value,
            onValueChange = onValueChange,
            label = localization.dateOfBirthLabel.build(),
            errorMessage = error
        )
    }
}