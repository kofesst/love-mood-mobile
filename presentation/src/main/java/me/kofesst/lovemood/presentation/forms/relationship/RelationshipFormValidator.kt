package me.kofesst.lovemood.presentation.forms.relationship

import me.kofesst.lovemood.core.models.Relationship
import me.kofesst.lovemood.features.validation.operations.DateValidation
import me.kofesst.lovemood.features.validation.operations.consumeOperations
import me.kofesst.lovemood.localization.AppLocalization
import me.kofesst.lovemood.presentation.forms.FormValidator
import me.kofesst.lovemood.presentation.forms.profile.ProfileFormValidator
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Валидатор формы отношений.
 */
@Singleton
class RelationshipFormValidator @Inject constructor(
    private val profileFormValidator: ProfileFormValidator,
    private val localization: AppLocalization
) : FormValidator<Relationship, RelationshipFormState> {
    companion object {
        val MIN_START_DATE = LocalDate.of(1900, 1, 1)
        val MAX_START_DATE = LocalDate.now()
    }

    override fun validate(form: RelationshipFormState): RelationshipFormState {
        val startDateError = consumeOperations(
            DateValidation.range(
                minDate = MIN_START_DATE,
                maxDate = MAX_START_DATE
            )
        ).validate(form.startDate)
        return form.copy(
            startDateError = startDateError?.let {
                localization.errors.fromValidationError(it)
            },
            partnerProfile = profileFormValidator.validate(form.partnerProfile)
        )
    }
}