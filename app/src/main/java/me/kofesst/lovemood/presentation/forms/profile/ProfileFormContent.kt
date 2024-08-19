package me.kofesst.lovemood.presentation.forms.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import me.kofesst.lovemood.app.LocalAppState
import me.kofesst.lovemood.app.LocalDateTimePattern
import me.kofesst.lovemood.core.models.Gender
import me.kofesst.lovemood.core.text.AppTextHolder
import me.kofesst.lovemood.core.ui.components.input.RadioInputField
import me.kofesst.lovemood.core.ui.components.input.RadioInputFieldItem
import me.kofesst.lovemood.core.ui.components.input.SmallImagePickerField
import me.kofesst.lovemood.core.ui.components.input.TextInputField
import me.kofesst.lovemood.ui.text.dictionary.shortLocalizedName
import me.kofesst.lovemood.ui.theme.containerColor

data class ProfileFormDictionary(
    val avatarPickerLabel: AppTextHolder,
    val avatarPickerAction: AppTextHolder,
    val usernameFieldLabel: AppTextHolder,
    val usernameFieldPlaceholder: AppTextHolder,
    val genderFieldLabel: AppTextHolder,
    val dateOfBirthFieldLabel: AppTextHolder
)

fun LazyListScope.profileFormContent(
    dictionary: ProfileFormDictionary,
    form: ProfileFormState,
    onFormAction: (ProfileFormAction) -> Unit
) {
    item(key = "profile_avatar_field") {
        AvatarPickerField(
            modifier = Modifier.fillMaxWidth(),
            dictionary = dictionary,
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
    dictionary: ProfileFormDictionary,
    content: ByteArray,
    onContentLoad: (ByteArray) -> Unit
) {
    val appState = LocalAppState.current
    SmallImagePickerField(
        modifier = modifier,
        loadedContent = content,
        onContentLoad = onContentLoad,
        coroutineScope = appState.coroutineScope,
        label = dictionary.avatarPickerLabel.string(),
        action = dictionary.avatarPickerAction.string()
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
        error = error,
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
                    text = gender.shortLocalizedName.string(),
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
        error = error,
        singleLine = true,
        maxLines = 1
    )
}