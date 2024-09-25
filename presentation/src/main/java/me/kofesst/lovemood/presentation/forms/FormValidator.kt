package me.kofesst.lovemood.presentation.forms

/**
 * Валидатор формы [Form] модели [Model].
 */
interface FormValidator<Model : Any, Form : FormState<Model>> {
    /**
     * Валидирует форму [form] и возвращает её новое состояние после валидации.
     */
    fun validate(form: Form): Form
}