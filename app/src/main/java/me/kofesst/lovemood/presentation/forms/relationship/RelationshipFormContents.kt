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
import me.kofesst.lovemood.app.LocalDateTimePattern
import me.kofesst.lovemood.app.dictionary
import me.kofesst.lovemood.core.ui.components.input.TextInputField
import me.kofesst.lovemood.localization.dictionary.screens.forms.RelationshipFormDictionary
import me.kofesst.lovemood.presentation.forms.profile.ProfileFormAction
import me.kofesst.lovemood.presentation.forms.profile.ProfileFormContents

object RelationshipFormContents {
    @Composable
    fun SelectingPartnerPictureStageContent(
        modifier: Modifier = Modifier,
        form: RelationshipFormState,
        onProfileFormAction: (ProfileFormAction) -> Unit
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
                    text = "Фотография партнера",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Выберите фотографию своего партнера",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Не стесняйтесь! Видеть её будете только Вы",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            ProfileFormContents.AvatarPickerField(
                modifier = Modifier.fillMaxWidth(),
                content = form.partnerProfile.avatarContent
            ) {
                onProfileFormAction(
                    ProfileFormAction.AvatarContentChanged(it)
                )
            }
        }
    }

    @Composable
    fun EnteringDataStageContent(
        modifier: Modifier = Modifier,
        form: RelationshipFormState,
        onFormAction: (RelationshipFormAction) -> Unit,
        onProfileFormAction: (ProfileFormAction) -> Unit
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
                    text = "Данные отношений",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = "Укажите информацию о партнере и отношениях",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Не переживайте! Все данные хранятся только на Вашем устройстве и не улетят ни в чьи злые руки",
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(space = 20.dp)
            ) {
                val dictionary = dictionary.screens.relationshipForm
                val relationshipFormDictionary = dictionary.relationshipFormDictionary
                val profileFormDictionary = dictionary.profileFormDictionary
                ProfileFormContents.UsernameInputField(
                    modifier = Modifier.fillMaxWidth(),
                    dictionary = profileFormDictionary,
                    value = form.partnerProfile.username,
                    onValueChange = {
                        onProfileFormAction(
                            ProfileFormAction.UsernameChanged(it)
                        )
                    },
                    error = form.partnerProfile.usernameError?.string()
                )
                ProfileFormContents.DateOfBirthInputField(
                    modifier = Modifier.fillMaxWidth(),
                    dictionary = profileFormDictionary,
                    value = form.partnerProfile.dateOfBirth,
                    onValueChange = {
                        onProfileFormAction(
                            ProfileFormAction.DateOfBirthChanged(it)
                        )
                    },
                    error = form.partnerProfile.dateOfBirthError?.string()
                )
                StartDateField(
                    modifier = Modifier.fillMaxWidth(),
                    dictionary = relationshipFormDictionary,
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
    }

    @Composable
    fun StartDateField(
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
}