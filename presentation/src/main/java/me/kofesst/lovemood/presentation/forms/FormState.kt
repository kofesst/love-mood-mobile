package me.kofesst.lovemood.presentation.forms

/**
 * Базовое представление формы ввода.
 *
 * [Model] - модель, которая заполняется в форме.
 */
abstract class FormState<Model : Any> {
    /**
     * Правильно ли заполнена форма.
     */
    abstract val isValid: Boolean

    /**
     * Заполнена ли форма.
     */
    abstract val isFilled: Boolean

    /**
     * Конвертирует форму в модель [Model].
     */
    abstract fun asModel(): Model

    /**
     * Конвертирует форму из модели [model].
     */
    abstract fun fromModel(model: Model): FormState<Model>
}