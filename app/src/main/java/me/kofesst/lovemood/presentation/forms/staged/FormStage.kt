package me.kofesst.lovemood.presentation.forms.staged

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.kofesst.lovemood.presentation.forms.FormAction
import me.kofesst.lovemood.presentation.forms.FormState

/**
 * Интерфейс этапа формы [Form] модели [Model].
 */
interface FormStage<Model : Any, Form : FormState<Model>, Action : FormAction<Model, Form>> {
    /**
     * Предикат условия перехода на следующий этап формы или отправки формы.
     */
    val continuePredicate: (Form) -> Boolean

    /**
     * Контент этапа формы.
     *
     * [modifier] - модификатор контента.
     *
     * [form] - текущее состояние формы.
     *
     * [onFormAction] - коллбек при совершении действия в форме.
     */
    @Composable
    fun Content(
        modifier: Modifier,
        form: Form,
        onFormAction: (Action) -> Unit
    )
}