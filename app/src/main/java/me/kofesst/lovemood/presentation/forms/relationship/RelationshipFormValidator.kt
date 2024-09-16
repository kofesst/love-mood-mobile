package me.kofesst.lovemood.presentation.forms.relationship

import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.features.date.DateTimePattern
import me.kofesst.lovemood.features.validation.operations.DateValidation
import me.kofesst.lovemood.features.validation.operations.consumeOperations
import me.kofesst.lovemood.localization.dictionary.AppDictionary
import me.kofesst.lovemood.presentation.forms.FormValidator
import me.kofesst.lovemood.presentation.forms.profile.ProfileFormValidator
import me.kofesst.lovemood.ui.uiText
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Валидатор формы отношений.
 */
@Singleton
class RelationshipFormValidator @Inject constructor(
    private val profileFormValidator: ProfileFormValidator,
    private val dateTimePattern: DateTimePattern,
    private val dictionary: AppDictionary
) : FormValidator<Relationship, RelationshipFormState> {
    companion object {
        val MIN_START_DATE = LocalDate.of(1900, 1, 1)
        val MAX_START_DATE = LocalDate.now()
    }

    override fun validate(form: RelationshipFormState): RelationshipFormState {
        val startDateError = consumeOperations(
            DateValidation.cast(dateTimePattern),
            DateValidation.range(
                minDate = MIN_START_DATE,
                maxDate = MAX_START_DATE
            )
        ).validate(form.startDate)
        return form.copy(
            startDateError = startDateError?.uiText(dictionary.errors),
            partnerProfile = profileFormValidator.validate(form.partnerProfile)
        )
    }
}