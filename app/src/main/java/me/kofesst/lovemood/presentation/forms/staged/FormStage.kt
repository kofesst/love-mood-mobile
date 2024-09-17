package me.kofesst.lovemood.presentation.forms.staged

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.kofesst.lovemood.presentation.forms.BaseFormViewModel
import me.kofesst.lovemood.presentation.forms.FormAction
import me.kofesst.lovemood.presentation.forms.FormState

/**
 * Интерфейс этапа формы.
 */
interface FormStage<Model : Any, Form : FormState<Model>, Action : FormAction<Model, Form>, ViewModel : BaseFormViewModel<Model, Form, Action>> {
    /**
     * Предикат условия перехода на следующий этап формы или отправки формы.
     */
    val continuePredicate: (Form) -> Boolean

    /**
     * Контент этапа формы.
     *
     * [modifier] - модификатор контента.
     *
     * [viewModel] - вью модель формы.
     */
    @Composable
    fun Content(
        modifier: Modifier,
        viewModel: ViewModel
    )
}