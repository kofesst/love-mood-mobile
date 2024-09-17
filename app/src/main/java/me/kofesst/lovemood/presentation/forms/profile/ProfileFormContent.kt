package me.kofesst.lovemood.presentation.forms.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.kofesst.lovemood.app.LocalDateTimePattern
import me.kofesst.lovemood.app.dictionary
import me.kofesst.lovemood.core.models.Gender
import me.kofesst.lovemood.core.ui.components.input.ImagePicker
import me.kofesst.lovemood.core.ui.components.input.RadioInputField
import me.kofesst.lovemood.core.ui.components.input.RadioInputFieldItem
import me.kofesst.lovemood.core.ui.components.input.TextInputField
import me.kofesst.lovemood.localization.dictionary.screens.forms.ProfileFormDictionary
import me.kofesst.lovemood.ui.shortUiText
import me.kofesst.lovemood.ui.theme.containerColor

@Composable
fun ProfilePictureStage(
    modifier: Modifier,
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
                text = "Фотография профиля",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Выберите фотографию профиля",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Не стесняйтесь! Видеть её будете только Вы",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        AvatarPickerField(
            modifier = Modifier.fillMaxWidth(),
            content = form.avatarContent
        ) {
            onFormAction(
                ProfileFormAction.AvatarContentChanged(it)
            )
        }
    }
}

@Composable
fun ProfileDataStage(
    modifier: Modifier = Modifier,
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
                text = "Данные профиля",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Укажите информацию о себе",
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
            val dictionary = dictionary.screens.userProfileForm.formDictionary
            UsernameInputField(
                modifier = Modifier.fillMaxWidth(),
                dictionary = dictionary,
                value = form.username,
                onValueChange = {
                    onFormAction(
                        ProfileFormAction.UsernameChanged(it)
                    )
                },
                error = form.usernameError?.string()
            )
            DateOfBirthInputField(
                modifier = Modifier.fillMaxWidth(),
                dictionary = dictionary,
                value = form.dateOfBirth,
                onValueChange = {
                    onFormAction(
                        ProfileFormAction.DateOfBirthChanged(it)
                    )
                },
                error = form.dateOfBirthError?.string()
            )
        }
    }
}

@Deprecated("Use layout from StagedFormLayout.kt")
fun LazyListScope.profileFormContent(
    dictionary: ProfileFormDictionary,
    form: ProfileFormState,
    onFormAction: (ProfileFormAction) -> Unit
) {
    item(key = "profile_avatar_field") {
        AvatarPickerField(
            modifier = Modifier.fillMaxWidth(),
            content = form.avatarContent
        ) {
            onFormAction(
                ProfileFormAction.AvatarContentChanged(it)
            )
        }
    }
    item(key = "profile_username_field") {
        UsernameInputField(
            modifier = Modifier.fillMaxWidth(),
            dictionary = dictionary,
            value = form.username,
            onValueChange = {
                onFormAction(
                    ProfileFormAction.UsernameChanged(it)
                )
            },
            error = form.usernameError?.string()
        )
    }
    item(key = "profile_gender_field") {
        GenderRadioField(
            modifier = Modifier.fillMaxWidth(),
            dictionary = dictionary,
            value = form.gender,
            onValueChange = {
                onFormAction(
                    ProfileFormAction.GenderChanged(it)
                )
            }
        )
    }
    item(key = "profile_date_of_birth_field") {
        DateOfBirthInputField(
            modifier = Modifier.fillMaxWidth(),
            dictionary = dictionary,
            value = form.dateOfBirth,
            onValueChange = {
                onFormAction(
                    ProfileFormAction.DateOfBirthChanged(it)
                )
            },
            error = form.dateOfBirthError?.string()
        )
    }
}

@Composable
private fun AvatarPickerField(
    modifier: Modifier = Modifier,
    content: ByteArray,
    onContentLoad: (ByteArray) -> Unit
) {
    ImagePicker(
        modifier = modifier,
        imageContent = content,
        onContentChange = onContentLoad,
        selectImageAction = {
            Text(text = "Выбрать изображение")
        },
        removeImageAction = {
            Text(text = "Удалить изображение")
        }
    )
}

@Composable
private fun UsernameInputField(
    modifier: Modifier = Modifier,
    dictionary: ProfileFormDictionary,
    value: String,
    onValueChange: (String) -> Unit,
    error: String?
) {
    TextInputField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = dictionary.usernameFieldLabel.string(),
        placeholder = dictionary.usernameFieldPlaceholder.string(),
        maxSymbols = 36,
        errorMessage = error,
        singleLine = true,
        maxLines = 1
    )
}

@Composable
private fun GenderRadioField(
    modifier: Modifier = Modifier,
    dictionary: ProfileFormDictionary,
    value: Gender,
    onValueChange: (Gender) -> Unit
) {
    RadioInputField(
        modifier = modifier,
        selected = value,
        items = Gender.entries.toList(),
        label = dictionary.genderFieldLabel.string()
    ) { gender, selected ->
        RadioInputFieldItem(
            isSelected = selected,
            itemContainer = { modifier, inner ->
                Surface(
                    modifier = modifier,
                    color = gender.containerColor,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = RoundedCornerShape(20.dp),
                    onClick = { onValueChange(gender) }
                ) {
                    inner()
                }
            },
            itemContent = {
                Text(
                    modifier = Modifier.weight(1.0f),
                    text = gender.shortUiText.string(),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}

@Composable
fun DateOfBirthInputField(
    modifier: Modifier = Modifier,
    dictionary: ProfileFormDictionary,
    value: String,
    onValueChange: (String) -> Unit,
    error: String?
) {
    val dateTimePattern = LocalDateTimePattern.current
    TextInputField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = dictionary.dateOfBirthFieldLabel.string(),
        placeholder = dateTimePattern.displayDatePattern,
        errorMessage = error,
        singleLine = true,
        maxLines = 1
    )
}