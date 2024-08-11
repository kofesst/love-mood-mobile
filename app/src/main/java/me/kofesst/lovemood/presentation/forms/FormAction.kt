package me.kofesst.lovemood.presentation.forms

abstract class FormAction<Model : Any, Form : FormState<Model>> {
    open fun applyToForm(currentForm: Form): Form {
        return currentForm
    }
}