package me.kofesst.lovemood.presentation.forms.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.kofesst.lovemood.app.LocalAppState
import me.kofesst.lovemood.app.LocalDateTimePattern
import me.kofesst.lovemood.core.ui.components.input.SmallImagePickerField
import me.kofesst.lovemood.core.ui.components.input.TextInputField
import me.kofesst.lovemood.localization.dictionary.screens.forms.ProfileFormDictionary

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
        errorMessage = error,
        singleLine = true,
        maxLines = 1
    )
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